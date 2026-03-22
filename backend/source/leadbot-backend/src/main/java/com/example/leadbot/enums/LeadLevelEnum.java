package com.example.leadbot.enums;

import lombok.Getter;

/**
 * 线索等级枚举
 */
@Getter
public enum LeadLevelEnum {

    HIGH("high", "高意向"),
    FOLLOW("follow", "待跟进"),
    NEW("new", "新线索");

    private final String code;
    private final String label;

    LeadLevelEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static LeadLevelEnum fromCode(String code) {
        for (LeadLevelEnum value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("未知线索等级: " + code);
    }
}
