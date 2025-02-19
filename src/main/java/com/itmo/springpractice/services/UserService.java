package com.itmo.springpractice.services;

import com.itmo.springpractice.models.database.entities.User;
import com.itmo.springpractice.models.dtos.requests.UserInfoReq;
import com.itmo.springpractice.models.dtos.responses.UserInfoResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface UserService {
    UserInfoResp getUser(Long id);
    // List<UserInfoResp> getAllUsers();
    Page<UserInfoResp> getAllUsersWithPagination(Integer page, Integer pageSize, String sortParam, Sort.Direction sortDirect, String filter);
    User getUserFromDB(Long id);

    UserInfoResp addUser(UserInfoReq userInfoReq);

    UserInfoResp updateUser(Long id, UserInfoReq userInfoReq);
    User updateUserCars(User updatedUser);

    void deleteUser(Long id);
}
