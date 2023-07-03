package com.cleverpine.viravamanageacesscore.service.mock.resource;

import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravamanageacesscore.service.contract.resource.AMResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AMResourceServiceMockImpl implements AMResourceService {

    @Override
    public List<Resource> getAll() {
        return List.of(new Resource(1L, "resource1", "Resource 1"), new Resource(2L, "resource2", "Resource 2"));
    }

    @Override
    public Resource create(Resource resource) {
        return new Resource(1L, resource.getName(), resource.getDisplayName());
    }

    @Override
    public void deleteById(long id) {

    }
}
