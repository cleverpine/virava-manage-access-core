package com.cleverpine.viravamanageacesscore.controller.user;

import com.cleverpine.cpspringerrorutil.util.ResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.api.AmUserApi;
import com.cleverpine.viravamanageacesscore.mapper.AMUserMapper;
import com.cleverpine.viravamanageacesscore.model.AMUserInfo;
import com.cleverpine.viravamanageacesscore.model.AMUserInfoResponse;
import com.cleverpine.viravamanageacesscore.model.AMUsersTableData;
import com.cleverpine.viravamanageacesscore.model.AMUsersTableDataResponse;
import com.cleverpine.viravamanageacesscore.service.impl.AMInternalUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AMUserController implements AmUserApi {

    private final AMUserMapper amUserMapper;
    private final AMInternalUserService amInternalUserService;
    private final ResponseEntityUtil<AMUsersTableDataResponse, AMUsersTableData> amUsersTableDataResponseEntityUtil;
    private final ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil;

    public AMUserController(
            AMUserMapper amUserMapper,
            AMInternalUserService amInternalUserService,
            ResponseEntityUtil<AMUsersTableDataResponse, AMUsersTableData> amUsersTableDataResponseEntityUtil,
            ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil) {
        this.amInternalUserService = amInternalUserService;
        this.amUserMapper = amUserMapper;
        this.amUsersTableDataResponseEntityUtil = amUsersTableDataResponseEntityUtil;
        this.amUserInfoResponseEntityUtil = amUserInfoResponseEntityUtil;
    }

    @Override
    public ResponseEntity<AMUsersTableDataResponse> getAllUsers() {
        var amUsersTableDataDTO = amInternalUserService.getUsersTableData();
        return amUsersTableDataResponseEntityUtil.ok(amUserMapper.amUsersTableDataDTOToAMUsersTableData(amUsersTableDataDTO));
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> getUser(Long id) {
        var user = amInternalUserService.getUser(id);
        return amUserInfoResponseEntityUtil.ok(amUserMapper.userToAMUserInfo(user));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        amInternalUserService.deleteUser(id);
        return amUserInfoResponseEntityUtil.noContent();
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> createUser(AMUserInfo amUserInfo) {
        var user = amInternalUserService.createUser(amUserMapper.amUserInfoToUser(amUserInfo));
        return amUserInfoResponseEntityUtil.created(amUserMapper.userToAMUserInfo(user));
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> updateUser(Long id, AMUserInfo amUserInfo) {
        var user = amInternalUserService.updateUser(id, amUserMapper.amUserInfoToUser(amUserInfo));
        return amUserInfoResponseEntityUtil.ok(amUserMapper.userToAMUserInfo(user));
    }
}
