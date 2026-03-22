package com.example.leadbot.channel;

import com.example.leadbot.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道配置实体
 *
 * 用于维护系统中的获客渠道及其预算说明。
 */
@Entity
@Table(name = "channel_setting")
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 渠道名称
     * 例如：抖音投流 / 公众号留资 / AI 外呼渠道
     */
    @Column(nullable = false, length = 128)
    private String name;

    /**
     * 预算说明
     * 例如：当前预算：¥12,000 / 周
     */
    @Column(length = 255)
    private String budget;
}
