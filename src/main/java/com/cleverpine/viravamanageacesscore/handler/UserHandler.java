package com.cleverpine.viravamanageacesscore.handler;

import com.cleverpine.viravabackendcommon.dto.User;

public interface UserHandler {

    User create(User user);

    User update(String username, User user);

    boolean delete(String username);
}
