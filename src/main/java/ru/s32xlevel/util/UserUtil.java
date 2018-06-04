package ru.s32xlevel.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.s32xlevel.model.Role;
import ru.s32xlevel.model.User;

import java.util.Collections;

public class UserUtil {

    private UserUtil() {
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        if (user.getRoles() == null || user.getRoles().size() < 1) {
            user.setRoles(Collections.singleton(Role.ROLE_USER));
        }
        String password = user.getPassword();
        user.setPassword(StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }


}
