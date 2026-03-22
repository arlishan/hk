package com.example.leadbot.chat;

import com.example.leadbot.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AI 聊天消息实体
 *
 * 用途：
 * 1. 存储前端与 AI 助手的对话记录
 * 2. 支持按角色区分 user / bot 消息
 * 3. 便于后续扩展会话、上下文、消息追踪等能力
 */
@Entity
@Table(name = "chat_message")
@Data
@EqualsAndHashCode(callSuper = true)
public class ChatMessage extends BaseEntity {

    /**
     * 主键 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 消息角色
     * 可选值建议：
     * - user
     * - bot
     */
    @Column(nullable = false, length = 32)
    private String role;

    /**
     * 消息内容
     */
    @Column(nullable = false, length = 2000)
    private String text;
}
