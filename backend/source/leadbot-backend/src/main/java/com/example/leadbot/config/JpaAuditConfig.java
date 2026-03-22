package com.example.leadbot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 启用 JPA 审计能力，用于自动填充实体中的：
 * - createdAt
 * - updatedAt
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {
}
