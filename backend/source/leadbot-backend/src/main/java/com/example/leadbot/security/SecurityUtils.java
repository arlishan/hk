package com.example.leadbot.security;

import com.example.leadbot.user.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 安全上下文辅助工具类
 *
 * 当前项目在 JWT 过滤器中把已登录用户对象放入 request attribute：
 * - key: loginUser
 *
 * 该工具类用于统一获取当前登录用户，便于控制器、服务层扩展使用。
 */
public final class SecurityUtils {

    public static final String LOGIN_USER_ATTR = "loginUser";

    private SecurityUtils() {
    }

    /**
     * 从当前请求中获取已登录用户
     *
     * @param request HttpServletRequest
     * @return 当前登录用户；如果不存在则返回 null
     */
    public static User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object value = request.getAttribute(LOGIN_USER_ATTR);
        if (value instanceof User user) {
            return user;
        }
        return null;
    }

    /**
     * 判断当前请求是否已登录
     *
     * @param request HttpServletRequest
     * @return true 表示已登录
     */
    public static boolean isLogin(HttpServletRequest request) {
        return getLoginUser(request) != null;
    }

    /**
     * 获取当前登录用户名
     *
     * @param request HttpServletRequest
     * @return 用户名；如果未登录则返回 null
     */
    public static String getLoginUsername(HttpServletRequest request) {
        User user = getLoginUser(request);
        return user != null ? user.getUsername() : null;
    }

    /**
     * 获取当前登录用户角色
     *
     * @param request HttpServletRequest
     * @return 角色；如果未登录则返回 null
     */
    public static String getLoginUserRole(HttpServletRequest request) {
        User user = getLoginUser(request);
        return user != null ? user.getRole() : null;
    }

    /**
     * 判断当前用户是否为指定角色
     *
     * @param request HttpServletRequest
     * @param role    角色编码
     * @return true 表示匹配
     */
    public static boolean hasRole(HttpServletRequest request, String role) {
        String currentRole = getLoginUserRole(request);
        return currentRole != null && currentRole.equals(role);
    }
}
