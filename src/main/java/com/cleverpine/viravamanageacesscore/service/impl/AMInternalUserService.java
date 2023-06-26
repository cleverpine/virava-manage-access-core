package com.cleverpine.viravamanageacesscore.service.impl;

import com.cleverpine.cpspringerrorutil.exception.BadRequestException;
import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.factory.ResourceHandlerFactory;
import com.cleverpine.viravamanageacesscore.handler.UserHandler;
import com.cleverpine.viravamanageacesscore.principal.AMUserPrincipalProvider;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMUserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AMInternalUserService {

    private final AMUserService amUserService;
    private final UserHandler userHandler;
    private final AMUserPrincipalProvider amUserPrincipalProvider;
    private final ResourceHandlerFactory resourceHandlerFactory;

    public AMInternalUserService(AMUserService amUserService,
                                 UserHandler userHandler,
                                 AMUserPrincipalProvider amUserPrincipalProvider,
                                 ResourceHandlerFactory resourceHandlerFactory) {
        this.amUserService = amUserService;
        this.userHandler = userHandler;
        this.amUserPrincipalProvider = amUserPrincipalProvider;
        this.resourceHandlerFactory = resourceHandlerFactory;
    }

    public List<User> getAllUsers() {
        var allowedUsernames = getAllowedUsernames();

        return amUserService.getAll()
                .stream()
                .filter(fetchedUser -> allowedUsernames.contains(fetchedUser.getUsername()))
                .toList();
    }

    public User getUser(Long id) {
        var user = amUserService.getById(id);
        validateUserActionAllowed(user.getUsername());
        return user;
    }

    public void deleteUser(Long id) {
        var user = amUserService.getById(id);
        var username = user.getUsername();
        validateUserActionAllowed(username);

        //TODO add transaction management
        amUserService.delete(id);
        var userDeleted = userHandler.delete(username);
    }

    public User createUser(User user) {
        //TODO add transaction management
        var result = amUserService.create(user);
        var userCreated = userHandler.create(user);
        return result;
    }

    public User updateUser(Long id, User userWithUpdates) {
        var user = amUserService.getById(id);
        var username = user.getUsername();
        validateUserActionAllowed(username);

        //TODO add transaction management
        var result = amUserService.update(id, userWithUpdates);
        var userUpdated = userHandler.update(username, userWithUpdates);
        return result;
    }

    public void validateUserActionAllowed(Long userId) {
        var user = amUserService.getById(userId);
        validateUserActionAllowed(user.getUsername());
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
}
