package com.cleverpine.viravamanageacesscore.config;

import com.cleverpine.cpspringerrorutil.util.GenericResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ListResponseEntityUtil;
import com.cleverpine.cpspringerrorutil.util.ResponseEntityUtil;
import com.cleverpine.viravamanageacesscore.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMResponseEntityUtilConfig {

    @Bean
    public GenericResponseEntityUtil amGenericResponseEntityUtil() {
        return new GenericResponseEntityUtil();
    }

    @Bean
    public ListResponseEntityUtil<AMUserListResponse, AMUser> amUserListResponseEntityUtil() {
        return new ListResponseEntityUtil<>(AMUserListResponse.class, AMUser.class);
    }

    @Bean
    public ResponseEntityUtil<AMUserInfoResponse, AMUserInfo> amUserInfoResponseEntityUtil() {
        return new ResponseEntityUtil<>(AMUserInfoResponse.class, AMUserInfo.class);
    }

    @Bean
    public ListResponseEntityUtil<AMPermissionListResponse, AMPermission> amPermissionListResponseEntityUtil() {
        return new ListResponseEntityUtil<>(AMPermissionListResponse.class, AMPermission.class);
    }

    @Bean
    public ResponseEntityUtil<AMPermissionResponse, AMPermission> amPermissionResponseEntityUtil() {
        return new ResponseEntityUtil<>(AMPermissionResponse.class, AMPermission.class);
    }

    @Bean
    public ListResponseEntityUtil<AMResourceListResponse, AMResource> amResourceListResponseEntityUtil() {
        return new ListResponseEntityUtil<>(AMResourceListResponse.class, AMResource.class);
    }

    @Bean
    public ResponseEntityUtil<AMResourceResponse, AMResource> amResourceResponseEntityUtil() {
        return new ResponseEntityUtil<>(AMResourceResponse.class, AMResource.class);
    }
}
