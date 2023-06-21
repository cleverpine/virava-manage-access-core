package com.cleverpine.viravamanageacesscore.service.mock.user;

import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AMUserServiceMockImpl implements AMUserService {

    private final static long USER_ID = 1L;
    private final static String USERNAME = "testUser";

    @Override
    public User getById(long id) {
        var user = new User(USER_ID, USERNAME);

        generateUserData(user);

        return user;
    }

    @Override
    public User getByUsername(String username) {
        var user = new User(USER_ID, username);

        generateUserData(user);

        return user;
    }

    @Override
    public List<User> getAll() {
        var userOne = new User(1L, "U123456");
        var userTwo = new User(2L, "U123451");

        generateUserData(userOne);
        generateUserData(userTwo);

        return List.of(userOne, userTwo);
    }

    @Override
    public User create(User user) {
        return new User(user.getId(), user.getUsername(), user.getData(), user.getPermissions(), user.getResourcePermissions());
    }

    @Override
    public User update(long id, User user) {
        return new User(id, user.getUsername(), user.getData(), user.getPermissions(), user.getResourcePermissions());
    }

    @Override
    public void delete(long id) {
    }

    private void generateUserData(User user) {
        user.addData("email", "testmail@mail.com");
        user.addData("uNumber", "U123456");
    }
}

