package com.cleverpine.viravamanageacesscore.service.mock.handler;

import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravamanageacesscore.handler.ResourcePermissionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcePermissionHandlerMockImpl implements ResourcePermissionHandler {
    @Override
    public Map<String, Map<String, Resource>> getAllResources(List<String> resources) {
        var result = new HashMap<String, Map<String, Resource>>();

        var resource1 = new Resource(1L, "resource1", "Resource 1");
        result.put("resource1", Map.of(resource1.getName(), resource1));

        return result;
    }
}
