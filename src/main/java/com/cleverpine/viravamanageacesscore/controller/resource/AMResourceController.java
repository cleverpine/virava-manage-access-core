package com.cleverpine.viravamanageacesscore.controller.resource;

import com.cleverpine.cpspringerrorutil.util.ListResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.api.AmResourceApi;
import com.cleverpine.viravamanageacesscore.factory.ResourceHandlerFactory;
import com.cleverpine.viravamanageacesscore.mapper.AMResourceMapper;
import com.cleverpine.viravamanageacesscore.model.AMCreateResourceRequest;
import com.cleverpine.viravamanageacesscore.model.AMResource;
import com.cleverpine.viravamanageacesscore.model.AMResourceListResponse;
import com.cleverpine.viravamanageacesscore.model.AMResourceResponse;
import com.cleverpine.viravamanageacesscore.service.contract.resource.AMResourceService;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AMResourceController implements AmResourceApi {

    private final AMResourceService amResourceService;
    private final AMResourceMapper amResourceMapper;
    private final AMUserService amUserService;
    private final ResourceHandlerFactory resourceHandlerFactory;
    private final ResponseEntityUtil<AMResourceResponse, AMResource> amResourceResponseEntityUtil;
    private final ListResponseEntityUtil<AMResourceListResponse, AMResource> amResourceListResponseEntityUtil;

    public AMResourceController(AMResourceService amResourceService,
                                AMResourceMapper amResourceMapper,
                                AMUserService amUserService,
                                ResourceHandlerFactory resourceHandlerFactory,
                                ResponseEntityUtil<AMResourceResponse, AMResource> amResourceResponseEntityUtil,
                                ListResponseEntityUtil<AMResourceListResponse, AMResource> amResourceListResponseEntityUtil) {
        this.amResourceService = amResourceService;
        this.amResourceMapper = amResourceMapper;
        this.amUserService = amUserService;
        this.resourceHandlerFactory = resourceHandlerFactory;
        this.amResourceResponseEntityUtil = amResourceResponseEntityUtil;
        this.amResourceListResponseEntityUtil = amResourceListResponseEntityUtil;
    }

    @Override
    public ResponseEntity<AMResourceResponse> createResource(AMCreateResourceRequest amCreateResourceRequest) {
        var result = amResourceService.create(amResourceMapper.amCreateResourceRequestToResource(amCreateResourceRequest));

        return amResourceResponseEntityUtil.created(amResourceMapper.resourceToAMResource(result));
    }

    @Override
    public ResponseEntity<Void> deleteResource(Long id) {
        amResourceService.deleteById(id);

        return amResourceResponseEntityUtil.noContent();
    }

    @Override
    public ResponseEntity<AMResourceListResponse> getAllResources() {
        var result = amResourceService.getAll();

        return amResourceListResponseEntityUtil.ok(amResourceMapper.resourceListToAMResourceList(result));
    }

    @Override
    public ResponseEntity<AMResourceListResponse> getUserResourcesByName(String username, String resourceName) {
        var user = amUserService.getByUsername(username);

        var result = resourceHandlerFactory.getHandler(resourceName).getResources(user);

        return amResourceListResponseEntityUtil.ok(amResourceMapper.resourceListToAMResourceList(result));
    }
}
