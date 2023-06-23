package com.cleverpine.viravamanageacesscore.controller.userpermission;

import com.cleverpine.cpspringerrorutil.util.GenericResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.api.AmUserPermissionApi;
import com.cleverpine.viravamanageacesscore.factory.ResourceHandlerFactory;
import com.cleverpine.viravamanageacesscore.mapper.AMUserPermissionMapper;
import com.cleverpine.viravamanageacesscore.model.AMResourcePermission;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMInternalUserService;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMUserService;
import com.cleverpine.viravamanageacesscore.service.contract.userpermission.AMUserPermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AMUserPermissionController implements AmUserPermissionApi {

    private final GenericResponseEntityUtil amGenericResponseEntityUtil;
    private final AMUserPermissionMapper amUserPermissionMapper;
    private final AMUserPermissionService amUserPermissionService;
//    private final AMUserService amUserService;
    private final AMInternalUserService amInternalUserService;
    private final ResourceHandlerFactory resourceHandlerFactory;

    public AMUserPermissionController(GenericResponseEntityUtil amGenericResponseEntityUtil,
                                      AMUserPermissionMapper amUserPermissionMapper,
                                      AMUserPermissionService amUserPermissionService,
//                                      AMUserService amUserService,
                                      AMInternalUserService amInternalUserService,
                                      ResourceHandlerFactory resourceHandlerFactory) {
        this.amGenericResponseEntityUtil = amGenericResponseEntityUtil;
        this.amUserPermissionMapper = amUserPermissionMapper;
        this.amUserPermissionService = amUserPermissionService;
        this.amInternalUserService = amInternalUserService;
//        this.amUserService = amUserService;
        this.resourceHandlerFactory = resourceHandlerFactory;
    }

    @Override
    public ResponseEntity<Void> assignPermission(Long userId, Long permissionId) {
        //TODO fix this
        var user = amInternalUserService.getUser(userId);
        amUserPermissionService.assignPermission(userId, permissionId);
        return amGenericResponseEntityUtil.noContent();
    }

    @Override
    public ResponseEntity<Void> assignResourcePermission(Long userId, String resourceName,
                                                         AMResourcePermission amResourcePermission) {
        var resourcePermission = amUserPermissionMapper.amResourcePermissionToResourcePermission(amResourcePermission);
        amUserPermissionService.assignResourcePermission(userId, resourceName, resourcePermission);

//        var user = amUserService.getById(userId);
        var user = amInternalUserService.getUser(userId);

        resourceHandlerFactory.getHandler(resourceName).assignResourcePermission(user, resourcePermission);

        return amGenericResponseEntityUtil.noContent();
    }
}
