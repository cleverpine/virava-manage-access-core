package com.cleverpine.viravamanageacesscore.service.mock.permission;

import com.cleverpine.viravabackendcommon.dto.Permission;
import com.cleverpine.viravamanageacesscore.service.contract.permission.AMPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AMPermissionServiceMockImpl implements AMPermissionService {

    @Override
    public List<Permission> getAll() {
        return List.of(
                new Permission(1L, "FirstMockPermission"),
                new Permission(2L, "SecondMockPermission"));
    }

    @Override
    public Permission create(Permission permission) {
        return new Permission(1L, permission.getName());
    }

    @Override
    public boolean checkIfExist(long id) {
        return true;
    }

    @Override
    public Permission get(long id) {
        return new Permission(id, "mockPermission");
    }
}
