package com.cleverpine.viravamanageacesscore.service.mock.handler;

import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.handler.UserHandler;
import org.springframework.stereotype.Service;

@Service
public class UserHandlerMockImpl implements UserHandler {

    @Override
    public boolean create(User user) {
        return true;
    }

    @Override
    public boolean update(String username, User user) {
        return true;
    }

    @Override
    public boolean delete(String username) {
        return true;
    }
}
