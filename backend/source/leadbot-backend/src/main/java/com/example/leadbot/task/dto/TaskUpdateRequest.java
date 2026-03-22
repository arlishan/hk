package com.example.leadbot.task.dto;

import lombok.Data;

/**
 * 任务更新请求 DTO
 */
@Data
public class TaskUpdateRequest {

    /**
     * 是否完成
     */
    private Boolean done;
}
