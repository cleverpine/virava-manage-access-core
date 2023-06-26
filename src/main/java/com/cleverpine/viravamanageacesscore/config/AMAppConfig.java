package com.cleverpine.viravamanageacesscore.config;

import com.cleverpine.cpspringerrorutil.util.GenericResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ListResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.aspect.TransactionalAspect;
import com.cleverpine.viravamanageacesscore.controller.permission.AMPermissionController;
import com.cleverpine.viravamanageacesscore.controller.resource.AMResourceController;
import com.cleverpine.viravamanageacesscore.controller.user.AMUserController;
import com.cleverpine.viravamanageacesscore.controller.userpermission.AMUserPermissionController;
import com.cleverpine.viravamanageacesscore.factory.ResourceHandlerFactory;
import com.cleverpine.viravamanageacesscore.handler.ResourceHandler;
import com.cleverpine.viravamanageacesscore.handler.ResourcePermissionHandler;
import com.cleverpine.viravamanageacesscore.handler.UserHandler;
import com.cleverpine.viravamanageacesscore.init.ViravaAccessManagementInitializer;
import com.cleverpine.viravamanageacesscore.mapper.*;
import com.cleverpine.viravamanageacesscore.model.*;
import com.cleverpine.viravamanageacesscore.principal.AMUserPrincipalProvider;
import com.cleverpine.viravamanageacesscore.service.contract.permission.AMPermissionService;
import com.cleverpine.viravamanageacesscore.service.contract.resource.AMResourceService;
import com.cleverpine.viravamanageacesscore.service.contract.user.AMUserService;
import com.cleverpine.viravamanageacesscore.service.contract.userpermission.AMUserPermissionService;
import com.cleverpine.viravamanageacesscore.service.impl.AMInternalUserService;
import com.cleverpine.viravamanageacesscore.service.mock.handler.ResourceHandlerMockImpl;
import com.cleverpine.viravamanageacesscore.service.mock.handler.ResourcePermissionHandlerMockImpl;
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
    public ResourceHandler<?> resourceHandler() {
        return new ResourceHandlerMockImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceHandlerFactory resourceHandlerFactory(ResourceHandler<?>... resourceHandlers) {
        return new ResourceHandlerFactory(List.of(resourceHandlers));
    }

    @Bean
    @ConditionalOnMissingBean
    public TransactionalAspect transactionalAspect() {
        return new TransactionalAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserHandler userHandler() {
        return new UserHandlerMockImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourcePermissionHandler resourcePermissionHandler() {
        return new ResourcePermissionHandlerMockImpl();
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
    public AMInternalUserService amInternalUserService(AMUserService amUserService,
                                                       AMUserMapper amUserMapper,
                                                       UserHandler userHandler,
                                                       ResourcePermissionHandler resourcePermissionHandler,
                                                       AMUserPrincipalProvider amUserPrincipalProvider,
                                                       ResourceHandlerFactory resourceHandlerFactory) {
        return new AMInternalUserService(amUserService, amUserMapper, userHandler, resourcePermissionHandler, amUserPrincipalProvider,
                resourceHandlerFactory);
    }

    @Bean
    public AMUserController amUserController(
            AMUserMapper amUserMapper,
            AMInternalUserService amInternalUserService,
            ListResponseEntityUtil<AMUserListResponse, AMUser> amUserListResponseEntityUtil,
            ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil) {
        return new AMUserController(amUserMapper, amInternalUserService, amUserListResponseEntityUtil, amUserInfoResponseEntityUtil);
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
            AMInternalUserService amInternalUserService,
            ResourceHandlerFactory resourceHandlerFactory) {
        return new AMUserPermissionController(amGenericResponseEntityUtil, amUserPermissionMapper, amUserPermissionService,
                amInternalUserService, resourceHandlerFactory);
    }

    @Bean
    public AMResourceController amResourceController(
            AMResourceService amResourceService,
            AMResourceMapper amResourceMapper,
            AMUserService amUserService,
            ResourceHandlerFactory resourceHandlerFactory,
            AMUserPrincipalProvider amUserPrincipalProvider,
            ListResponseEntityUtil<AMResourceListResponse, AMResource> amResourceListResponseEntityUtil,
            ResponseEntityUtil<AMResourceResponse, AMResource> amResourceResponseEntityUtil) {
        return new AMResourceController(amResourceService, amResourceMapper, amUserService, amUserPrincipalProvider, resourceHandlerFactory,
                amResourceResponseEntityUtil, amResourceListResponseEntityUtil);
    }
}
