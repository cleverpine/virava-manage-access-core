package com.cleverpine.viravamanageacesscore.handler;

import com.cleverpine.viravabackendcommon.dto.User;

public interface UserHandler {

    boolean create(User user);

    boolean update(String username, User user);

    boolean delete(String username);
}
