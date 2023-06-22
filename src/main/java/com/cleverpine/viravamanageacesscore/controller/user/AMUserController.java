package com.cleverpine.viravamanageacesscore.controller.user;

import com.cleverpine.cpspringerrorutil.util.ListResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.api.AmUserApi;
import com.cleverpine.viravamanageacesscore.mapper.AMUserMapper;
import com.cleverpine.viravamanageacesscore.model.AMUser;
import com.cleverpine.viravamanageacesscore.model.AMUserInfo;
import com.cleverpine.viravamanageacesscore.model.AMUserInfoResponse;
import com.cleverpine.viravamanageacesscore.model.AMUserListResponse;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMInternalUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AMUserController implements AmUserApi {

    //    private final AMUserService amUserService;
    private final AMUserMapper amUserMapper;
    //    private final UserHandler userHandler;
//    private final AMUserPrincipalProvider amUserPrincipalProvider;
//    private final ResourceHandlerFactory resourceHandlerFactory;
    private final AMInternalUserService amInternalUserService;
    private final ListResponseEntityUtil<AMUserListResponse, AMUser> amUserListResponseEntityUtil;
    private final ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil;

    public AMUserController(
//            AMUserService amUserService,
            AMUserMapper amUserMapper,
//            UserHandler userHandler,
//            AMUserPrincipalProvider amUserPrincipalProvider,
//            ResourceHandlerFactory resourceHandlerFactory,
            AMInternalUserService amInternalUserService,
            ListResponseEntityUtil<AMUserListResponse, AMUser> amUserListResponseEntityUtil,
            ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil) {
        this.amInternalUserService = amInternalUserService;
//        this.amUserService = amUserService;
        this.amUserMapper = amUserMapper;
//        this.userHandler = userHandler;
//        this.amUserPrincipalProvider = amUserPrincipalProvider;
//        this.resourceHandlerFactory = resourceHandlerFactory;
        this.amUserListResponseEntityUtil = amUserListResponseEntityUtil;
        this.amUserInfoResponseEntityUtil = amUserInfoResponseEntityUtil;
    }

    @Override
    public ResponseEntity<AMUserListResponse> getAllUsers() {
        var result = amInternalUserService.getAllUsers();
        return amUserListResponseEntityUtil.ok(amUserMapper.userListToAMUserList(result));
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> getUser(Long id) {
        var result = amInternalUserService.getUser(id);

        return amUserInfoResponseEntityUtil.ok(amUserMapper.userToAMUserInfo(result));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        amInternalUserService.deleteUser(id);
        return amUserInfoResponseEntityUtil.noContent();
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> createUser(AMUserInfo amUserInfo) {
        var result = amInternalUserService.createUser(amUserInfo);
        return amUserInfoResponseEntityUtil.created(amUserMapper.userToAMUserInfo(result));
    }

    @Override
    public ResponseEntity<AMUserInfoResponse> updateUser(Long id, AMUserInfo amUserInfo) {
        var result = amInternalUserService.updateUser(id, amUserInfo);
        return amUserInfoResponseEntityUtil.ok(amUserMapper.userToAMUserInfo(result));
    }
}
