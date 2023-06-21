package com.cleverpine.viravamanageacesscore.service.contract.resource;

import com.cleverpine.viravabackendcommon.dto.Resource;

import java.util.List;

public interface AMResourceService {

    List<Resource> getAll();

    Resource create(Resource resource);

    void deleteById(long id);
}
