package com.example.leadbot.auth.dto;

import com.example.leadbot.user.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    /**
     * JWT token
     */
    private String token;

    /**
     * 当前登录用户信息
     */
    private ProfileResponse user;
}
