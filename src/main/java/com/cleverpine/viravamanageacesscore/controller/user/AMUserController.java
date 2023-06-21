package com.cleverpine.viravamanageacesscore.controller.user;

import com.cleverpine.cpspringerrorutil.util.ListResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ResponseEntityUtil;
import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.api.AmUserApi;
import com.cleverpine.viravamanageacesscore.handler.UserHandler;
import com.cleverpine.viravamanageacesscore.mapper.AMUserMapper;
import com.cleverpine.viravamanageacesscore.model.AMUser;
import com.cleverpine.viravamanageacesscore.model.AMUserInfo;
import com.cleverpine.viravamanageacesscore.model.AMUserInfoResponse;
import com.cleverpine.viravamanageacesscore.model.AMUserListResponse;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AMUserController implements AmUserApi {

    private final AMUserService amUserService;
    private final AMUserMapper amUserMapper;
    private final UserHandler userHandler;
    private final ListResponseEntityUtil<AMUserListResponse, AMUser> amUserListResponseEntityUtil;
    private final ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil;

    public AMUserController(AMUserService amUserService,
                            AMUserMapper amUserMapper,
                            UserHandler userHandler,
                            ListResponseEntityUtil<AMUserListResponse, AMUser> amUserListResponseEntityUtil,
                            ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil) {
        this.amUserService = amUserService;
        this.amUserMapper = amUserMapper;
        this.userHandler = userHandler;
        this.amUserListResponseEntityUtil = amUserListResponseEntityUtil;
        this.amUserInfoResponseEntityUtil = amUserInfoResponseEntityUtil;
    }

    @Override
    public ResponseEntity<AMUserListResponse> getAllUsers() {
        var result = amUserService.getAll();

        return amUserListResponseEntityUtil.ok(amUserMapper.userListToAMUserList(result));
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> getUser(Long id) {
        var result = amUserService.getById(id);

        return amUserInfoResponseEntityUtil.ok(amUserMapper.userToAMUserInfo(result));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        var user = amUserService.getById(id);

        //TODO add transaction management
        amUserService.delete(id);
        var userDeleted = userHandler.delete(user.getUsername());

        return amUserInfoResponseEntityUtil.noContent();
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> createUser(AMUserInfo amUserInfo) {
        var user = amUserMapper.amUserInfoToUser(amUserInfo);

        //TODO add transaction management
        var result = amUserService.create(user);
        var userCreated = userHandler.create(user);

        return amUserInfoResponseEntityUtil.created(amUserMapper.userToAMUserInfo(result));
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> updateUser(Long id, AMUserInfo amUserInfo) {
        var user = amUserService.getById(id);
        var userWithUpdates = amUserMapper.amUserInfoToUser(amUserInfo);

        //TODO add transaction management
        var result = amUserService.update(id, userWithUpdates);
        var userUpdated = userHandler.update(user.getUsername(), userWithUpdates);

        return amUserInfoResponseEntityUtil.ok(amUserMapper.userToAMUserInfo(result));
    }
}
