package com.cleverpine.viravamanageacesscore.mapper;

import com.cleverpine.viravabackendcommon.dto.Permission;
import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.model.AMUser;
import com.cleverpine.viravamanageacesscore.model.AMUserInfo;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cleverpine.viravamanageacesscore.util.AMConstants.ALL;
import static com.cleverpine.viravamanageacesscore.util.AMConstants.DELIMETER;

@Mapper(componentModel = "spring")
public interface AMUserMapper extends AMUserPermissionMapper {
    AMUser userToAMUser(User user);

    AMUserInfo userToAMUserInfo(User user);

    List<AMUser> userListToAMUserList(List<User> userList);

    User amUserInfoToUser(AMUserInfo userInfo);

    default void mapResourcesInData(User user, Map<String, String> resourceNameDisplayNameMap) {
        var resourcePermissionEntityList = user.getResourcePermissions().getResourcePermissionMap();
        var permissionEntityList = user.getPermissions();

        if (resourcePermissionEntityList != null) {
            resourcePermissionEntityList.forEach((resourceName, resourcePermission) -> {
                var displayName = resourceNameDisplayNameMap.get(resourceName);
                if (resourcePermission.isAll()) {
                    user.addData(displayName, ALL);
                    return;
                }

                var resourceIds = resourcePermission.getIds();
                if (!CollectionUtils.isEmpty(resourceIds)) {
                    var resources = String.join(DELIMETER, resourceIds);
                    user.addData(displayName, resources);
                }
            });
        }

        if (permissionEntityList != null) {
            var permissionsString = permissionEntityList.stream().map(Permission::getName).collect(Collectors.joining(DELIMETER));
            user.addData("Roles", permissionsString);
        }
    }
}
