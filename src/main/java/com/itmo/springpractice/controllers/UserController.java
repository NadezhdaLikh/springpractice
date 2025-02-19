package com.itmo.springpractice.controllers;

import com.itmo.springpractice.models.dtos.requests.UserInfoReq;
import com.itmo.springpractice.models.dtos.responses.UserInfoResp;
import com.itmo.springpractice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserInfoResp getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /*@GetMapping("/all")
    public List<UserInfoResp> getAllUsers() {
        return userService.getAllUsers();
    }*/

    @GetMapping("/all")
    public Page<UserInfoResp> getAllUsers(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "lastName") String sortParam,
                                          @RequestParam(defaultValue = "ASC") Sort.Direction sortDirect,
                                          @RequestParam(required = false) String filter) {
        return userService.getAllUsersWithPagination(page, pageSize, sortParam, sortDirect, filter);
    }

    @PostMapping
    public UserInfoResp addUser(@RequestBody UserInfoReq userInfoReq) {
        return userService.addUser(userInfoReq);
    }

    @PutMapping("/{id}")
    public UserInfoResp updateUser(@PathVariable Long id, @RequestBody UserInfoReq userInfoReq) {
        return userService.updateUser(id, userInfoReq);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
