package com.cleverpine.viravamanageacesscore.controller.permission;

import com.cleverpine.cpspringerrorutil.util.ListResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.api.AmPermissionApi;
import com.cleverpine.viravamanageacesscore.mapper.AMPermissionMapper;
import com.cleverpine.viravamanageacesscore.model.AMCreatePermissionRequest;
import com.cleverpine.viravamanageacesscore.model.AMPermission;
import com.cleverpine.viravamanageacesscore.model.AMPermissionListResponse;
import com.cleverpine.viravamanageacesscore.model.AMPermissionResponse;
import com.cleverpine.viravamanageacesscore.service.contract.permission.AMPermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AMPermissionController implements AmPermissionApi {
    
    private final AMPermissionService amPermissionService;
    private final AMPermissionMapper amPermissionMapper;
    private final ListResponseEntityUtil<AMPermissionListResponse, AMPermission> amPermissionListResponseEntityUtil;
    private final ResponseEntityUtil<AMPermissionResponse, AMPermission> amPermissionResponseEntityUtil;

    public AMPermissionController(AMPermissionService amPermissionService, AMPermissionMapper amPermissionMapper,
                                  ListResponseEntityUtil<AMPermissionListResponse, AMPermission> amPermissionListResponseEntityUtil,
                                  ResponseEntityUtil<AMPermissionResponse, AMPermission> amPermissionResponseEntityUtil) {
        this.amPermissionService = amPermissionService;
        this.amPermissionMapper = amPermissionMapper;
        this.amPermissionListResponseEntityUtil = amPermissionListResponseEntityUtil;
        this.amPermissionResponseEntityUtil = amPermissionResponseEntityUtil;
    }

    @Override
    public ResponseEntity<AMPermissionResponse> createPermission(AMCreatePermissionRequest amCreatePermissionRequest) {
        var result = amPermissionService.create(amPermissionMapper.amCreatePermissionRequestToPermission(amCreatePermissionRequest));

        return amPermissionResponseEntityUtil.created(amPermissionMapper.permissionToAMPermission(result));
    }

    @Override
    public ResponseEntity<AMPermissionListResponse> getPermissions() {
        return amPermissionListResponseEntityUtil.ok(amPermissionMapper.permissionListToAMPermissionList(amPermissionService.getAll()));
    }
}
