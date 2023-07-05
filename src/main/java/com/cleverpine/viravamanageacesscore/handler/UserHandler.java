package com.cleverpine.viravamanageacesscore.handler;

import com.cleverpine.viravabackendcommon.dto.User;

import java.util.List;

public interface UserHandler {

    User create(User user);

    User update(String username, User user);

    boolean delete(String username);

    List<String> getUsersTableOrder();
}
