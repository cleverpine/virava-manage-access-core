package com.cleverpine.viravamanageacesscore.factory;

import com.cleverpine.viravamanageacesscore.handler.ResourceHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceHandlerFactory {

    private final Map<String, ResourceHandler<?>> handlerMap = new HashMap<>();

    public ResourceHandlerFactory(List<ResourceHandler<?>> handlers) {
        for (ResourceHandler<?> handler : handlers) {
            handlerMap.put(handler.getResourceName(), handler);
        }
    }

    public ResourceHandler<?> getHandler(String type) {
        ResourceHandler<?> handler = handlerMap.get(type);
        if (handler == null) {
            throw new IllegalArgumentException("Invalid resource type: " + type);
        }
        return handler;
    }
}

