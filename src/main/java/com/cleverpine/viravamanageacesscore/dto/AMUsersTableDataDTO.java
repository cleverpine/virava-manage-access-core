package com.cleverpine.viravamanageacesscore.dto;

import com.cleverpine.viravabackendcommon.dto.User;

import java.util.ArrayList;
import java.util.List;

public class AMUsersTableDataDTO {

    private List<String> usersTableOrder;
    private List<User> users;

    public AMUsersTableDataDTO() {
        usersTableOrder = new ArrayList<>();
        users = new ArrayList<>();
    }

    public AMUsersTableDataDTO(List<String> usersTableOrder, List<User> users) {
        this.usersTableOrder = usersTableOrder;
        this.users = users;
    }

    public List<String> getUsersTableOrder() {
        return usersTableOrder;
    }

    public void setUsersTableOrder(List<String> usersTableOrder) {
        this.usersTableOrder = usersTableOrder;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
