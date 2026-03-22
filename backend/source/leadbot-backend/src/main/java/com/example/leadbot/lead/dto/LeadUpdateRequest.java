package com.example.leadbot.lead.dto;

import lombok.Data;

/**
 * 线索更新请求 DTO
 *
 * 说明：
 * 1. 所有字段均为可选，用于支持局部更新（PATCH）
 * 2. 当前主要用于客户信息维护、状态流转与最近跟进时间更新
 */
@Data
public class LeadUpdateRequest {

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 客户状态
     * 示例：新线索 / 待跟进 / 待回访 / 已预约 / 已跟进
     */
    private String status;

    /**
     * 最近跟进时间文案
     * 示例：刚刚 / 今天 10:20 / 昨天 18:40
     */
    private String lastFollow;
}
