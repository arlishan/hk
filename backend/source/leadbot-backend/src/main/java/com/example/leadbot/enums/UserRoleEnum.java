package com.example.leadbot.enums;

import lombok.Getter;

/**
 * 系统用户角色枚举
 *
 * <p>用于统一维护系统中的角色编码，便于：
 * <ul>
 *   <li>权限控制</li>
 *   <li>角色判断</li>
 *   <li>初始化默认用户</li>
 *   <li>前后端角色字段约定</li>
 * </ul>
 */
@Getter
public enum UserRoleEnum {

    /**
     * 系统管理员
     */
    ADMIN("ADMIN"),

    /**
     * 业务经理
     */
    MANAGER("MANAGER"),

    /**
     * 普通成员
     */
    USER("USER");

    /**
     * 角色编码
     */
    private final String code;

    UserRoleEnum(String code) {
        this.code = code;
    }
}
