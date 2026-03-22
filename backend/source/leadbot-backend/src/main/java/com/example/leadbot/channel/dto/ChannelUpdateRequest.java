package com.example.leadbot.channel.dto;

import lombok.Data;

/**
 * 渠道更新请求 DTO
 */
@Data
public class ChannelUpdateRequest {

    /**
     * 渠道名称
     */
    private String name;

    /**
     * 预算说明
     */
    private String budget;
}
