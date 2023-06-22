package com.cleverpine.viravamanageacesscore.service.contract.user;

import com.cleverpine.cpspringerrorutil.exception.BadRequestException;
import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.factory.ResourceHandlerFactory;
import com.cleverpine.viravamanageacesscore.handler.UserHandler;
import com.cleverpine.viravamanageacesscore.mapper.AMUserMapper;
import com.cleverpine.viravamanageacesscore.model.AMUserInfo;
import com.cleverpine.viravamanageacesscore.principal.AMUserPrincipalProvider;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AMInternalUserService {

    private final AMUserService amUserService;
    private final AMUserMapper amUserMapper;
    private final UserHandler userHandler;
    private final AMUserPrincipalProvider amUserPrincipalProvider;
    private final ResourceHandlerFactory resourceHandlerFactory;

    public AMInternalUserService(AMUserService amUserService,
                                 AMUserMapper amUserMapper,
                                 UserHandler userHandler,
                                 AMUserPrincipalProvider amUserPrincipalProvider,
                                 ResourceHandlerFactory resourceHandlerFactory) {
        this.amUserService = amUserService;
        this.amUserMapper = amUserMapper;
        this.userHandler = userHandler;
        this.amUserPrincipalProvider = amUserPrincipalProvider;
        this.resourceHandlerFactory = resourceHandlerFactory;
    }

    public List<User> getAllUsers() {
        var filteredUsernames = getAllowedUsernames();

        return amUserService.getAll()
                .stream()
                .filter(fetchedUser -> filteredUsernames.contains(fetchedUser.getUsername()))
                .toList();
    }

    public User getUser(Long id) {
        var user = amUserService.getById(id);
        validateUserCanModifyUser(user.getUsername());
        return user;
    }

    public void deleteUser(Long id) {
        var user = amUserService.getById(id);

        validateUserCanModifyUser(user.getUsername());

        //TODO add transaction management
        amUserService.delete(id);
        var userDeleted = userHandler.delete(user.getUsername());
    }

    public User createUser(AMUserInfo amUserInfo) {
        var user = amUserMapper.amUserInfoToUser(amUserInfo);

        //TODO add transaction management
        var result = amUserService.create(user);
        var userCreated = userHandler.create(user);

        return result;
    }

    public User updateUser(Long id, AMUserInfo amUserInfo) {
        var user = amUserService.getById(id);
        var userWithUpdates = amUserMapper.amUserInfoToUser(amUserInfo);

        //TODO add transaction management
        var result = amUserService.update(id, userWithUpdates);
        var userUpdated = userHandler.update(user.getUsername(), userWithUpdates);

        return result;
    }

    private void validateUserCanModifyUser(String username) {
        var allowedUserIds = getAllowedUsernames();

        if (!allowedUserIds.contains(username)) {
            throw new BadRequestException(String.format("Current user not allowed to update user with id %s", username));
        }
    }

    private Set<String> getAllowedUsernames() {
        var username = amUserPrincipalProvider.getUsername();
        var user = amUserService.getByUsername(username);
        return resourceHandlerFactory.getHandler("USER").getResources(user)
                .stream()
                .map(Resource::getName)
                .collect(Collectors.toSet());
    }
}
