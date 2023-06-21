package com.cleverpine.viravamanageacesscore.service.contract.user;

import com.cleverpine.viravabackendcommon.dto.User;

import java.util.List;

public interface AMUserService {

    User getById(long id);

    User getByUsername(String username);

    List<User> getAll();

    User create(User user);

    User update(long id, User user);

    void delete(long id);
}
