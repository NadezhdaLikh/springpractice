package com.itmo.springpractice.services.impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springpractice.exceptions.CustomBackendException;
import com.itmo.springpractice.models.database.entities.User;
import com.itmo.springpractice.models.database.repositories.UserRepository;
import com.itmo.springpractice.models.dtos.requests.UserInfoReq;
import com.itmo.springpractice.models.dtos.responses.UserInfoResp;
import com.itmo.springpractice.models.enums.UserStatus;
import com.itmo.springpractice.services.UserService;
import com.itmo.springpractice.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public User getUserFromDB(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        final String errorMessage = String.format("Sorry, user with id %d is not found:(", id);

        return optionalUser.orElseThrow(() -> new CustomBackendException(errorMessage, HttpStatus.NOT_FOUND));
    }

    @Override
    public UserInfoResp getUser(Long id) {
        User user = getUserFromDB(id);
        return objectMapper.convertValue(user, UserInfoResp.class);
    }

    /*@Override
    public List<UserInfoResp> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> objectMapper.convertValue(user, UserInfoResp.class))
                .collect(Collectors.toList());
    }*/

    @Override
    public Page<UserInfoResp> getAllUsersWithPagination(Integer page, Integer pageSize, String sortParam, Sort.Direction sortDirect, String filter) {
        Page<User> users;

        Pageable pageRequest = PaginationUtils.makePageRequest(page, pageSize, sortParam, sortDirect);

        if (StringUtils.hasText(filter)) {
            users = userRepository.findAllFiltered(pageRequest, filter);
        } else users = userRepository.findAll(pageRequest);

        List<UserInfoResp> content = users.getContent().stream()
                .map(u -> objectMapper.convertValue(u, UserInfoResp.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, users.getNumberOfElements());
    }

    @Override
    public UserInfoResp addUser(UserInfoReq userInfoReq) {
        if (!EmailValidator.getInstance().isValid(userInfoReq.getEmail())) {
            throw new CustomBackendException("Email is invalid!", HttpStatus.BAD_REQUEST);
        }

        userRepository.findByEmail(userInfoReq.getEmail()).ifPresent(user -> {
            final String errorMessage = String.format("User with email %s already exists!", userInfoReq.getEmail());
            throw new CustomBackendException(errorMessage, HttpStatus.CONFLICT);
        });

        User user = objectMapper.convertValue(userInfoReq, User.class);
        user.setStatus(UserStatus.ACTIVE);

        return objectMapper.convertValue(userRepository.save(user), UserInfoResp.class);
    }

    @Override
    public UserInfoResp updateUser(Long id, UserInfoReq userInfoReq) {
        User user = getUserFromDB(id);

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

        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    public User updateUserCars(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    @Override
    public void invalidateSession() {
        // logic

        String email = UserInfoReq.Fields.email;
        String age = UserInfoReq.Fields.age;
    }
}
