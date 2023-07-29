package com.rancard.auth.service;



import com.querydsl.core.types.Predicate;
import com.rancard.auth.models.dto.EditPasswordDto;
import com.rancard.auth.models.dto.EditUserDto;
import com.rancard.auth.models.dto.SignupDto;
import com.rancard.auth.models.mongo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

    User getUser(String id);

    User getUserByUsername(String username);

    User getUserByPhone(String phone);

    User getUserByEmail(String email);

    Page<User> getAllUsers(Predicate spec, Pageable pageable);

    List<User> getUserByIds(List<String> userIds);

    User signUpUser(SignupDto signUpUserDto);

    User updateUser(EditUserDto editUserDto);

    User removeUserElevatedRoles(String userId);

    void updateUserPassword(EditPasswordDto editPasswordDto);

    void deleteUser(String id);

    long userCount();
}
