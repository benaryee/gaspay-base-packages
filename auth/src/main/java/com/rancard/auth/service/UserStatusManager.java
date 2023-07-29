package com.rancard.auth.service;


import com.rancard.auth.models.mongo.User;

public interface UserStatusManager {
    void updateUserStatus(String username, boolean authSuccessful);
    void blockUser(User user);
}
