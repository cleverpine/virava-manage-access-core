package com.cleverpine.viravamanageacesscore.mapper;

import com.cleverpine.viravabackendcommon.dto.ResourcePermission;
import com.cleverpine.viravabackendcommon.dto.ResourcePermissions;
import com.cleverpine.viravamanageacesscore.model.AMResourcePermission;
import com.cleverpine.viravamanageacesscore.model.AMResourcePermissions;
import org.mapstruct.Mapper;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface AMUserPermissionMapper {

    ResourcePermission amResourcePermissionToResourcePermission(AMResourcePermission amResourcePermission);
    
    default AMResourcePermissions resourcePermissionsToAMResourcePermissions(ResourcePermissions resourcePermissions) {
        if (resourcePermissions == null) {
            return null;
        }

        var amResourcePermissions = new AMResourcePermissions();

        var resourcePermissionMap = resourcePermissions.getResourcePermissionMap();
        for (var entry : resourcePermissionMap.entrySet()) {
            var key = entry.getKey();
            var resourcePermission = entry.getValue();

            var isAll = resourcePermission.isAll();
            var ids = new ArrayList<>(resourcePermission.getIds());

            var amResourcePermission = new AMResourcePermission();
            amResourcePermission.setAll(isAll);
            amResourcePermission.setIds(ids);

            amResourcePermissions.putResourcePermissionMapItem(key, amResourcePermission);
        }

        return amResourcePermissions;
    }
}
