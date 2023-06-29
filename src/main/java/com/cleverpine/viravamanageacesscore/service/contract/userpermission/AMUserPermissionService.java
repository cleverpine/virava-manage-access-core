package com.cleverpine.viravamanageacesscore.service.contract.userpermission;


import com.cleverpine.viravabackendcommon.dto.ResourcePermission;
import com.cleverpine.viravabackendcommon.dto.ResourcePermissions;

public interface AMUserPermissionService {

    void assignPermission(long userId, long permissionId);

    void assignResourcePermission(long userId, String resourceName, ResourcePermission resourcePermission);

    void assignPermissions(long userId, long[] permissionIds);

    void assignResourcePermissions(long userId, ResourcePermissions resourcePermissions);

    void deleteResourcePermissionsByUserId(long userId);

    void deletePermissionsByUserId(long userId);

    void deletePermissionByResourceNameAndResourceId(String resourceName, long resourceId);
}
