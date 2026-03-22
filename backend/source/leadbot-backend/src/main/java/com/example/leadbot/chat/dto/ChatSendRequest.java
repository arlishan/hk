package com.example.leadbot.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * AI 聊天发送请求 DTO
 */
@Data
public class ChatSendRequest {

    /**
     * 用户输入内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String text;
}
