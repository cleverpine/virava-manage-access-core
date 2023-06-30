package com.cleverpine.viravamanageacesscore.service.contract.permission;

import com.cleverpine.viravabackendcommon.dto.Permission;

import java.util.List;

public interface AMPermissionService {
    List<Permission> getAll();

    Permission create(Permission permission);

    Permission get(long id);

    Permission getByName(String name);

    boolean checkIfExist(long id);
}
