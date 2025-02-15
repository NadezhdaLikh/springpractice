package com.itmo.springpractice.services;

import com.itmo.springpractice.models.dtos.requests.UserInfoReq;
import com.itmo.springpractice.models.dtos.responses.UserInfoResp;

import java.util.List;

public interface UserService {
    UserInfoResp getUser(Long id);
    List<UserInfoResp> getAllUsers();

    UserInfoResp addUser(UserInfoReq userInfoReq);
    UserInfoResp updateUser(Long id, UserInfoReq userInfoReq);
    void deleteUser(Long id);
}
