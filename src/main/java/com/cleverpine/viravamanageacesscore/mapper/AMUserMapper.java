package com.cleverpine.viravamanageacesscore.mapper;

import com.cleverpine.viravabackendcommon.dto.User;
import com.cleverpine.viravamanageacesscore.model.AMUser;
import com.cleverpine.viravamanageacesscore.model.AMUserInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AMUserMapper extends AMUserPermissionMapper {
    AMUser userToAMUser(User user);

    AMUserInfo userToAMUserInfo(User user);

    List<AMUser> userListToAMUserList(List<User> userList);

    User amUserInfoToUser(AMUserInfo userInfo);
}
