package com.cleverpine.viravamanageacesscore.service.contract.permission;

import com.cleverpine.viravabackendcommon.dto.Permission;

import java.util.List;

public interface AMPermissionService {
    List<Permission> getAll();

    Permission create(Permission permission);

    boolean checkIfExist(long id);
}
