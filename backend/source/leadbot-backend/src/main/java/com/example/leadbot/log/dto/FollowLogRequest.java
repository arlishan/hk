package com.example.leadbot.log.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 跟进记录新增请求 DTO
 */
@Data
public class FollowLogRequest {

    /**
     * 跟进标题
     */
    @NotBlank(message = "跟进标题不能为空")
    private String title;

    /**
     * 跟进内容说明
     */
    private String desc;
}
