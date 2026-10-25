package org.example.login.service;

import org.example.login.config.UserProperties;
import org.example.login.exception.BadRequestException;
import org.example.login.exception.BusinessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserStore userStore;
    private final UserProperties props;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserStore userStore, UserProperties props) {
        this.userStore = userStore;
        this.props = props;
    }

    public void register(String username, String rawPassword) {
        validate(username, rawPassword);

        if (userStore.exists(username)) {
            throw new BusinessException("用户名已存在");
        }

        // 密码加密存储
        String encoded = passwordEncoder.encode(rawPassword);
        if (!userStore.register(username, encoded)) {
            throw new BusinessException("用户名已存在");
        }
        userStore.persist();
    }

    public void login(String username, String rawPassword) {
        validate(username, rawPassword);

        String storedHash = userStore.getPassword(username);
        // 统一返回相同错误，防止攻击者探测用户名是否存在
        if (storedHash == null || !passwordEncoder.matches(rawPassword, storedHash)) {
            throw new BusinessException("用户名不存在或密码错误");
        }
    }

    private void validate(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("用户名不能为空");
        }
        if (!username.matches(props.getUsernamePattern())) {
            throw new BadRequestException("用户名不符合正则规则");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("密码不能为空");
        }
        if (password.length() < props.getPasswordMinLength()) {
            throw new BadRequestException("密码长度不能少于 " + props.getPasswordMinLength() + " 位");
        }
    }
}