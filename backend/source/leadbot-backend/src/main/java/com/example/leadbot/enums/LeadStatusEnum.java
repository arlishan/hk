package com.example.leadbot.enums;

import lombok.Getter;

/**
 * 线索状态枚举
 */
@Getter
public enum LeadStatusEnum {

    NEW("新线索"),
    FOLLOWING("待跟进"),
    CALLBACK("待回访"),
    APPOINTED("已预约"),
    DONE("已跟进");

    private final String label;

    LeadStatusEnum(String label) {
        this.label = label;
    }
}
