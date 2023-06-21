package com.cleverpine.viravamanageacesscore.mapper;

import com.cleverpine.viravabackendcommon.dto.Permission;
import com.cleverpine.viravamanageacesscore.model.AMCreatePermissionRequest;
import com.cleverpine.viravamanageacesscore.model.AMPermission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AMPermissionMapper {

    AMPermission permissionToAMPermission(Permission permission);

    List<AMPermission> permissionListToAMPermissionList(List<Permission> permissionList);

    Permission amCreatePermissionRequestToPermission(AMCreatePermissionRequest amPermission);
}
