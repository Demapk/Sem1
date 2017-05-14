package com.demes.service.interfaces;

import com.demes.entity.User;
import com.demes.web.dto.UserDto;

import java.util.Collection;

public interface IUserService {
    User createUserAccount(UserDto accountDto);

    String checkUserIsExists(String username, String email);

    User save(User user, boolean changePassword);

    User findUserByEmail(String email);

    User changeUserPassword(User user, String password);

    boolean checkNewPassword(String old, String newP, String newMatch);

    Collection<User> findAllNonAdminUsers();

    void deleteUser(Long id);

    String enableOrDisableUser(Long userId);
}
