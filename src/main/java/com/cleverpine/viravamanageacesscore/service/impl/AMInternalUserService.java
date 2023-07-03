package com.cleverpine.viravamanageacesscore.service.impl;

import com.cleverpine.cpspringerrorutil.exception.BadRequestException;
import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravabackendcommon.dto.ResourcePermission;
import com.cleverpine.viravabackendcommon.dto.ResourcePermissions;
import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.annotation.AMTransactional;
import com.cleverpine.viravamanageacesscore.factory.ResourceHandlerFactory;
import com.cleverpine.viravamanageacesscore.handler.ResourcePermissionHandler;
import com.cleverpine.viravamanageacesscore.handler.UserHandler;
import com.cleverpine.viravamanageacesscore.mapper.AMUserMapper;
import com.cleverpine.viravamanageacesscore.principal.AMUserPrincipalProvider;
import com.cleverpine.viravamanageacesscore.service.contract.permission.AMPermissionService;
import com.cleverpine.viravamanageacesscore.service.contract.resource.AMResourceService;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMUserService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AMInternalUserService {

    private final AMUserService amUserService;
    private final AMPermissionService amPermissionService;
    private final AMResourceService amResourceService;
    private final AMUserMapper amUserMapper;
    private final UserHandler userHandler;
    private final ResourcePermissionHandler resourcePermissionHandler;
    private final AMUserPrincipalProvider amUserPrincipalProvider;
    private final ResourceHandlerFactory resourceHandlerFactory;

    public AMInternalUserService(AMUserService amUserService,
                                 AMPermissionService amPermissionService,
                                 AMResourceService amResourceService,
                                 AMUserMapper amUserMapper,
                                 UserHandler userHandler,
                                 ResourcePermissionHandler resourcePermissionHandler,
                                 AMUserPrincipalProvider amUserPrincipalProvider,
                                 ResourceHandlerFactory resourceHandlerFactory) {
        this.amUserService = amUserService;
        this.amPermissionService = amPermissionService;
        this.amResourceService = amResourceService;
        this.amUserMapper = amUserMapper;
        this.userHandler = userHandler;
        this.resourcePermissionHandler = resourcePermissionHandler;
        this.amUserPrincipalProvider = amUserPrincipalProvider;
        this.resourceHandlerFactory = resourceHandlerFactory;
    }

    public List<User> getAllUsers() {
        var allowedUsernames = getAllowedUsernames();

        var users = amUserService.getAll()
                .stream()
                .filter(fetchedUser -> allowedUsernames.contains(fetchedUser.getUsername()))
                .toList();

        enrichUsers(users);

        return users;
    }

    public User getUser(Long id) {
        var user = amUserService.getById(id);
        validateUserActionAllowed(user.getUsername());
        return user;
    }

    @AMTransactional
    public void deleteUser(Long id) {
        var user = amUserService.getById(id);
        var username = user.getUsername();
        validateUserActionAllowed(username);

        var userDeleted = userHandler.delete(username);
        amUserService.delete(id);
    }

    @AMTransactional
    public User createUser(User user) {
        var externalUser = userHandler.create(user);
        return amUserService.create(externalUser);
    }

    @AMTransactional
    public User updateUser(Long id, User userWithUpdates) {
        var user = amUserService.getById(id);
        var username = user.getUsername();
        validateUserActionAllowed(username);

        var externalUser = userHandler.update(username, userWithUpdates);
        return amUserService.update(id, externalUser);
    }

    @AMTransactional
    public void assignPermission(Long userId, Long permissionId) {
        var user = amUserService.getById(userId);
        user.addPermission(amPermissionService.get(permissionId));
        this.updateUser(userId, user);
    }

    @AMTransactional
    public void assignResourcePermission(Long userId, String resourceName, ResourcePermission resourcePermission) {
        var user = getUser(userId);
        user.getResourcePermissions().addResourcePermission(resourceName, resourcePermission);
        this.updateUser(userId, user);
    }

    private void enrichUsers(List<User> users) {
        resourcePermissionHandler.mapResourcePermissions(users);
        enrichUserData(users);
    }

    private void enrichUserData(List<User> users) {
        var resourceNameDisplayNameMap = createResourceNameDisplayNameMap();
        users.forEach(user -> amUserMapper.mapResourcesInData(user, resourceNameDisplayNameMap));
    }

    private void validateUserActionAllowed(String username) {
        var allowedUsernames = getAllowedUsernames();

        if (!allowedUsernames.contains(username)) {
            throw new BadRequestException(String.format("Current user not allowed to update user with username %s", username));
        }
    }

    private Set<String> getAllowedUsernames() {
        var username = amUserPrincipalProvider.getUsername();
        var principalUser = amUserService.getByUsername(username);
        return resourceHandlerFactory.getHandler("USER").getResources(principalUser)
                .stream()
                .map(Resource::getName)
                .collect(Collectors.toSet());
    }

    private Map<String, String> createResourceNameDisplayNameMap() {
        return amResourceService.getAll()
                .stream()
                .collect(Collectors.toMap(Resource::getName, Resource::getDisplayName));
    }
}
