package com.cleverpine.viravamanageacesscore.service.mock.handler;

import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.handler.UserHandler;
import org.springframework.stereotype.Service;

@Service
public class UserHandlerMockImpl implements UserHandler {

    @Override
    public User create(User user) {
        return user;
    }

    @Override
    public User update(String username, User user) {
        return user;
    }

    @Override
    public boolean delete(String username) {
        return true;
    }
}
