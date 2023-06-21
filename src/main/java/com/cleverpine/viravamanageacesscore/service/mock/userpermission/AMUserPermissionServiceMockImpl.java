package com.cleverpine.viravamanageacesscore.service.mock.userpermission;

import com.cleverpine.viravabackendcommon.dto.ResourcePermission;
import com.cleverpine.viravabackendcommon.dto.ResourcePermissions;
import com.cleverpine.viravamanageacesscore.service.contract.userpermission.AMUserPermissionService;
import org.springframework.stereotype.Service;

@Service
public class AMUserPermissionServiceMockImpl implements AMUserPermissionService {

    @Override
    public void assignPermission(long userId, long permissionId) {
        //assign permission to user
    }

    @Override
    public void assignResourcePermission(long userId, String resourceName, ResourcePermission resourcePermission) {
        //assign resource permission to user
    }

    @Override
    public void assignPermissions(long userId, long[] permissionIds) {
        // assign multiple permissions to user
    }

    @Override
    public void assignResourcePermissions(long userId, ResourcePermissions resourcePermissions) {
        // assign multiple resource permissions to user
    }

    @Override
    public void deleteResourcePermissionsByUserId(long userId) {
        // delete user resource permissions
    }

    @Override
    public void deletePermissionsByUserId(long userId) {
        // delete user permissions
    }
}
