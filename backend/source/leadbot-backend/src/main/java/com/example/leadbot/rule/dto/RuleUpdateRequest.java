package com.example.leadbot.rule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 机器人规则更新请求 DTO
 */
@Data
public class RuleUpdateRequest {

    /**
     * 触达时间窗
     */
    @NotBlank(message = "触达时间窗不能为空")
    private String timeRange;

    /**
     * 跟进节奏规则
     */
    @NotBlank(message = "跟进节奏不能为空")
    private String followRule;

    /**
     * 行业策略规则
     */
    @NotBlank(message = "行业策略不能为空")
    private String industryRule;
}
