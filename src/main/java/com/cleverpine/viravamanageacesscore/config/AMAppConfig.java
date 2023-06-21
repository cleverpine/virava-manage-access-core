package com.cleverpine.viravamanageacesscore.config;

import com.cleverpine.cpspringerrorutil.util.GenericResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ListResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.controller.permission.AMPermissionController;
import com.cleverpine.viravamanageacesscore.controller.resource.AMResourceController;
import com.cleverpine.viravamanageacesscore.controller.user.AMUserController;
import com.cleverpine.viravamanageacesscore.controller.userpermission.AMUserPermissionController;
import com.cleverpine.viravamanageacesscore.factory.ResourceHandlerFactory;
import com.cleverpine.viravamanageacesscore.handler.UserHandler;
import com.cleverpine.viravamanageacesscore.init.ViravaAccessManagementInitializer;
import com.cleverpine.viravamanageacesscore.mapper.*;
import com.cleverpine.viravamanageacesscore.model.*;
import com.cleverpine.viravamanageacesscore.service.contract.permission.AMPermissionService;
import com.cleverpine.viravamanageacesscore.service.contract.resource.AMResourceService;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMUserService;
import com.cleverpine.viravamanageacesscore.service.contract.userpermission.AMUserPermissionService;
import com.cleverpine.viravamanageacesscore.service.mock.handler.ResourceHandlerMockImpl;
import com.cleverpine.viravamanageacesscore.service.mock.handler.UserHandlerMockImpl;
import com.cleverpine.viravamanageacesscore.service.mock.permission.AMPermissionServiceMockImpl;
import com.cleverpine.viravamanageacesscore.service.mock.resource.AMResourceServiceMockImpl;
import com.cleverpine.viravamanageacesscore.service.mock.user.AMUserServiceMockImpl;
import com.cleverpine.viravamanageacesscore.service.mock.userpermission.AMUserPermissionServiceMockImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import({ViravaAccessManagementInitializer.class, AMResponseEntityUtilConfig.class})
public class AMAppConfig {

    @Bean
    @ConditionalOnMissingBean
    public AMUserService amUserService() {
        return new AMUserServiceMockImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public AMPermissionService amPermissionService() {
        return new AMPermissionServiceMockImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public AMUserPermissionService amUserPermissionService() {
        return new AMUserPermissionServiceMockImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public AMResourceService amResourceService() {
        return new AMResourceServiceMockImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceHandlerFactory resourceHandlerFactory() {
        return new ResourceHandlerFactory(List.of(new ResourceHandlerMockImpl()));
    }

    @Bean
    @ConditionalOnMissingBean
    public UserHandler userHandler() {
        return new UserHandlerMockImpl();
    }

    @Bean
    public AMUserMapper amUserMapper() {
        return new AMUserMapperImpl();
    }

    @Bean
    public AMPermissionMapper amPermissionMapper() {
        return new AMPermissionMapperImpl();
    }

    @Bean
    public AMUserPermissionMapper amUserPermissionMapper() {
        return new AMUserPermissionMapperImpl();
    }

    @Bean
    public AMResourceMapper amResourceMapper() {
        return new AMResourceMapperImpl();
    }

    @Bean
    public AMUserController amUserController(
            AMUserService amUserService,
            AMUserMapper amUserMapper,
            UserHandler userHandler,
            ListResponseEntityUtil<AMUserListResponse, AMUser> amUserListResponseEntityUtil,
            ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil) {
        return new AMUserController(amUserService, amUserMapper, userHandler, amUserListResponseEntityUtil, amUserInfoResponseEntityUtil);
    }

    @Bean
    public AMPermissionController amPermissionController(
            AMPermissionService amPermissionService,
            AMPermissionMapper amPermissionMapper,
            ListResponseEntityUtil<AMPermissionListResponse, AMPermission> amPermissionListResponseEntityUtil,
            ResponseEntityUtil<AMPermissionResponse, AMPermission> amPermissionResponseEntityUtil) {
        return new AMPermissionController(
                amPermissionService, amPermissionMapper, amPermissionListResponseEntityUtil, amPermissionResponseEntityUtil);
    }

    @Bean
    public AMUserPermissionController amUserPermissionController(
            GenericResponseEntityUtil amGenericResponseEntityUtil,
            AMUserPermissionMapper amUserPermissionMapper,
            AMUserPermissionService amUserPermissionService,
            AMUserService amUserService,
            ResourceHandlerFactory resourceHandlerFactory) {
        return new AMUserPermissionController(
                amGenericResponseEntityUtil, amUserPermissionMapper, amUserPermissionService, amUserService, resourceHandlerFactory);
    }

    @Bean
    public AMResourceController amResourceController(
            AMResourceService amResourceService,
            AMResourceMapper amResourceMapper,
            AMUserService amUserService,
            ResourceHandlerFactory resourceHandlerFactory,
            ListResponseEntityUtil<AMResourceListResponse, AMResource> amResourceListResponseEntityUtil,
            ResponseEntityUtil<AMResourceResponse, AMResource> amResourceResponseEntityUtil) {
        return new AMResourceController(
                amResourceService, amResourceMapper, amUserService, resourceHandlerFactory, amResourceResponseEntityUtil,
                amResourceListResponseEntityUtil);
    }
}
