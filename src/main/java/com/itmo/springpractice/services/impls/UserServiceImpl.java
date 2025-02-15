package com.itmo.springpractice.services.impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springpractice.models.database.entities.User;
import com.itmo.springpractice.models.database.repositories.UserRepository;
import com.itmo.springpractice.models.dtos.requests.UserInfoReq;
import com.itmo.springpractice.models.dtos.responses.UserInfoResp;
import com.itmo.springpractice.models.enums.UserStatus;
import com.itmo.springpractice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    private User getUserFromDB(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("From getUserFromDB(): sorry, user with id {} is not found:(", id);
        }
        return optionalUser.orElse(new User());
    }

    @Override
    public UserInfoResp getUser(Long id) {
        User user = getUserFromDB(id);
        return objectMapper.convertValue(user, UserInfoResp.class);
    }

    @Override
    public List<UserInfoResp> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> objectMapper.convertValue(user, UserInfoResp.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserInfoResp addUser(UserInfoReq userInfoReq) {
        User user = objectMapper.convertValue(userInfoReq, User.class);
        user.setStatus(UserStatus.ACTIVE);

        return objectMapper.convertValue(userRepository.save(user), UserInfoResp.class);
    }

    @Override
    public UserInfoResp updateUser(Long id, UserInfoReq userInfoReq) {
        User user = getUserFromDB(id);
        if (user.getId() == null) {
            return objectMapper.convertValue(user, UserInfoResp.class);
        }

        user.setFirstName(userInfoReq.getFirstName() == null ? user.getFirstName() : userInfoReq.getFirstName());
        user.setMiddleName(userInfoReq.getMiddleName() == null ? user.getMiddleName() : userInfoReq.getMiddleName());
        user.setLastName(userInfoReq.getLastName() == null ? user.getLastName() : userInfoReq.getLastName());
        user.setGender(userInfoReq.getGender() == null ? user.getGender() : userInfoReq.getGender());
        user.setAge(userInfoReq.getAge() == null ? user.getAge() : userInfoReq.getAge());
        user.setEmail(userInfoReq.getEmail() == null ? user.getEmail() : userInfoReq.getEmail());
        user.setPassword(userInfoReq.getPassword() == null ? user.getPassword() : userInfoReq.getPassword());

        return objectMapper.convertValue(userRepository.save(user), UserInfoResp.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserFromDB(id);
        if (user.getId() == null) {
            return;
        }
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }
}
