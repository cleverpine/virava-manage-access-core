package com.cleverpine.viravamanageacesscore.service.mock.handler;

import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravabackendcommon.dto.ResourcePermission;
import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.handler.ResourceHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ResourceHandlerMockImpl implements ResourceHandler<Resource> {

    @Override
    public boolean canAccessAllResources(User user) {
        return true;
    }

    @Override
    public List<Resource> getAllResources() {
        return List.of(new Resource(1L, "resource1"), new Resource(2L, "resource2"));
    }

    @Override
    public List<Long> getUserResourceIds(User user) {
        return List.of(1L);
    }

    @Override
    public List<Resource> getUserAssignedResources(List<Long> resourceIds) {
        return List.of(new Resource(1L, "resource1"));
    }

    @Override
    public Function<Resource, Resource> mapToResource() {
        return resource -> resource;
    }

    @Override
    public boolean assignResourcePermission(User user, ResourcePermission resourcePermission) {
        return true;
    }

    @Override
    public String getResourceName() {
        return "mockResource";
    }
}
