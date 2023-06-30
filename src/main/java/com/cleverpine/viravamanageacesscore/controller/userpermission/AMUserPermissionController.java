package com.cleverpine.viravamanageacesscore.controller.userpermission;

import com.cleverpine.cpspringerrorutil.util.GenericResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.api.AmUserPermissionApi;
import com.cleverpine.viravamanageacesscore.mapper.AMUserPermissionMapper;
import com.cleverpine.viravamanageacesscore.model.AMResourcePermission;
import com.cleverpine.viravamanageacesscore.service.impl.AMInternalUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AMUserPermissionController implements AmUserPermissionApi {

    private final GenericResponseEntityUtil amGenericResponseEntityUtil;
    private final AMUserPermissionMapper amUserPermissionMapper;
    private final AMInternalUserService amInternalUserService;

    public AMUserPermissionController(GenericResponseEntityUtil amGenericResponseEntityUtil,
                                      AMUserPermissionMapper amUserPermissionMapper,
                                      AMInternalUserService amInternalUserService) {
        this.amGenericResponseEntityUtil = amGenericResponseEntityUtil;
        this.amUserPermissionMapper = amUserPermissionMapper;
        this.amInternalUserService = amInternalUserService;
    }

    @Override
    public ResponseEntity<Void> assignPermission(Long userId, Long permissionId) {
        amInternalUserService.assignPermission(userId, permissionId);
        return amGenericResponseEntityUtil.noContent();
    }

    @Override
    public ResponseEntity<Void> assignResourcePermission(Long userId, String resourceName,
                                                         AMResourcePermission amResourcePermission) {
        var resourcePermission = amUserPermissionMapper.amResourcePermissionToResourcePermission(amResourcePermission);
        amInternalUserService.assignResourcePermission(userId, resourceName, resourcePermission);
        return amGenericResponseEntityUtil.noContent();
    }
}
