package com.cleverpine.viravamanageacesscore.mapper;

import com.cleverpine.viravabackendcommon.dto.Resource;
import com.cleverpine.viravamanageacesscore.model.AMCreateResourceRequest;
import com.cleverpine.viravamanageacesscore.model.AMResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AMResourceMapper {
    AMResource resourceToAMResource(Resource result);

    List<AMResource> resourceListToAMResourceList(List<Resource> result);

    Resource amCreateResourceRequestToResource(AMCreateResourceRequest amCreateResourceRequest);
}