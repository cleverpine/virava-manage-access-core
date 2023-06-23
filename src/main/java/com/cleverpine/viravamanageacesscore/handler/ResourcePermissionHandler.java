package com.cleverpine.viravamanageacesscore.handler;

import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravabackendcommon.dto.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ResourcePermissionHandler {

    default void mapResourcePermissions(List<User> userDTO) {

        var resourcePermissionKeys = userDTO.stream()
                .map(user -> user.getResourcePermissions().getResourcePermissionMap().keySet())
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        var resourcePermissionWithNamesMap = getAllResources(resourcePermissionKeys);


        userDTO.forEach(user -> {
            var resourcePermissions = user.getResourcePermissions();

            resourcePermissions.getResourcePermissionMap()
                    .forEach((key, value) -> {
                        var resourcePermissionIdMap = resourcePermissionWithNamesMap.get(key);
                        var ids = new ArrayList<String>();

                        value.getIds().forEach(id -> {
                            var resource = resourcePermissionIdMap.get(id);

                            ids.add(resource.getName());
                        });

                        resourcePermissions.getResourcePermission(key).setIds(ids);
                    });

            user.setResourcePermissions(resourcePermissions);
        });

    }

    Map<String, Map<String, Resource>> getAllResources(List<String> resources);
}
