package com.example.leadbot.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前登录用户信息响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 角色编码，如 ADMIN / MANAGER / USER
     */
    private String role;

    /**
     * 头像简写
     */
    private String avatar;
}
