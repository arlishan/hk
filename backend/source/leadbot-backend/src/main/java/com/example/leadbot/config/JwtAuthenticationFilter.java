package com.example.leadbot.config;

import com.example.leadbot.security.JwtService;
import com.example.leadbot.user.User;
import com.example.leadbot.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 认证过滤器
 *
 * 作用：
 * 1. 从 Authorization 请求头中提取 Bearer Token
 * 2. 校验 Token 是否有效
 * 3. 根据 Token 中的用户名查询系统用户
 * 4. 将当前登录用户放入 request attribute，供后续 Controller / Service 使用
 *
 * 说明：
 * - 当前实现采用“轻量 request attribute 透传”方式，而不是完整写入 Spring Security 上下文
 * - 如果后续需要更标准的认证链，可以扩展为 SecurityContextHolder 模式
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            String token = authorization.substring(BEARER_PREFIX.length()).trim();

            if (!token.isEmpty() && jwtService.isValid(token)) {
                String username = jwtService.getUsername(token);
                User user = userRepository.findByUsername(username).orElse(null);

                if (user != null) {
                    request.setAttribute("loginUser", user);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
