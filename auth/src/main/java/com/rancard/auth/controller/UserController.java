package com.rancard.auth.controller;

import com.rancard.auth.models.mongo.User;
import com.rancard.auth.models.request.UserRequest;
import com.rancard.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth/user")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserRequest user , HttpServletRequest request) {

        String sessionId = request.getSession().getId();
        log.info("[{}] about to create user with payload : {} ", sessionId, user);
        return null;
    }
}
