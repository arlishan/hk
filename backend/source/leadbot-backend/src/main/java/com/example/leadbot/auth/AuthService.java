package com.example.leadbot.auth;

import com.example.leadbot.auth.dto.ChangePasswordRequest;
import com.example.leadbot.auth.dto.LoginRequest;
import com.example.leadbot.auth.dto.LoginResponse;
import com.example.leadbot.common.BusinessException;
import com.example.leadbot.common.OperateLogService;
import com.example.leadbot.security.JwtService;
import com.example.leadbot.user.ProfileResponse;
import com.example.leadbot.user.User;
import com.example.leadbot.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OperateLogService operateLogService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(401, "账号或密码错误"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "账号或密码错误");
        }

        operateLogService.save("AUTH", "LOGIN", user.getName(), "用户登录系统");

        String token = jwtService.generateToken(user.getUsername());
        ProfileResponse profile = new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getRole(),
                user.getAvatar()
        );

        return new LoginResponse(token, profile);
    }

    public ProfileResponse profile(User user) {
        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getRole(),
                user.getAvatar()
        );
    }

    public void changePassword(User loginUser, ChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.getOldPassword(), loginUser.getPassword())) {
            throw new BusinessException(400, "旧密码错误");
        }

        if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
            throw new BusinessException(400, "新密码不能为空");
        }

        if (request.getNewPassword().length() < 6) {
            throw new BusinessException(400, "新密码长度不能少于6位");
        }

        if (passwordEncoder.matches(request.getNewPassword(), loginUser.getPassword())) {
            throw new BusinessException(400, "新密码不能与旧密码相同");
        }

        loginUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(loginUser);

        operateLogService.save("AUTH", "CHANGE_PASSWORD", loginUser.getName(), "用户修改密码");
    }
}
