package com.cleverpine.viravamanageacesscore.handler;

import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravabackendcommon.dto.ResourcePermission;
import com.cleverpine.viravabackendcommon.dto.User;

import java.util.List;
import java.util.function.Function;

public interface ResourceHandler<ResourceDTO> {

    default List<Resource> getResources(User user) {
        List<ResourceDTO> resourceDTOList;

        if (canAccessAllResources(user)) {
            resourceDTOList = getAllResources();
        } else {
            var userResourceIds = getUserResourceIds(user);

            resourceDTOList = getUserAssignedResources(userResourceIds);
        }

        return resourceDTOList.stream()
                .map(mapToResource())
                .toList();
    }

    boolean canAccessAllResources(User user);

    List<ResourceDTO> getAllResources();

    List<Long> getUserResourceIds(User user);

    List<ResourceDTO> getUserAssignedResources(List<Long> resourceIds);

    Function<ResourceDTO, Resource> mapToResource();

    boolean assignResourcePermission(User user, ResourcePermission resourcePermission);

    String getResourceName();
}
