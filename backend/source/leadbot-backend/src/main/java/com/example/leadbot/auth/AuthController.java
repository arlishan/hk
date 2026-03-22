package com.example.leadbot.auth;

import com.example.leadbot.auth.dto.ChangePasswordRequest;
import com.example.leadbot.auth.dto.LoginRequest;
import com.example.leadbot.common.ApiResponse;
import com.example.leadbot.user.ProfileResponse;
import com.example.leadbot.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(authService.login(request));
    }

    @GetMapping("/profile")
    public ApiResponse<ProfileResponse> profile(@RequestAttribute("loginUser") User loginUser) {
        return ApiResponse.ok(authService.profile(loginUser));
    }

    @PostMapping("/change-password")
    public ApiResponse<?> changePassword(@RequestAttribute("loginUser") User loginUser,
                                         @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(loginUser, request);
        return ApiResponse.ok();
    }
}
