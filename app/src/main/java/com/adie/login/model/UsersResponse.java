package com.adie.login.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class UsersResponse {
    //dekapsulasi users dari output endpoint API
    @SerializedName("users")
    private List<Users> users;

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }


}
