package com.example.leadbot.rule;

import com.example.leadbot.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机器人规则配置实体
 *
 * 用于存储系统中的全局规则配置，包括：
 * 1. 触达时间窗
 * 2. 跟进节奏
 * 3. 行业策略
 *
 * 当前设计为单记录配置表，通常只保留一条有效配置。
 */
@Entity
@Table(name = "rule_setting")
@Data
@EqualsAndHashCode(callSuper = true)
public class RuleSetting extends BaseEntity {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 触达时间窗
     * 示例：工作日 09:00 - 11:30、14:00 - 18:00
     */
    @Column(name = "time_range", length = 255)
    private String timeRange;

    /**
     * 跟进规则
     * 示例：A 类每天；B 类隔天；C 类每周 2 次
     */
    @Column(name = "follow_rule", length = 1000)
    private String followRule;

    /**
     * 行业规则
     * 示例：教育优先推演示，本地生活优先推到店引流，医疗优先推咨询转化
     */
    @Column(name = "industry_rule", length = 1000)
    private String industryRule;
}
