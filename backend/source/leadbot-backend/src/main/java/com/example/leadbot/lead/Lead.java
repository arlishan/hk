package com.example.leadbot.lead;

import com.example.leadbot.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户线索实体
 *
 * 对应前端线索管理、客户详情、客户编辑、分页查询等功能。
 */
@Entity
@Table(name = "lead")
@Data
@EqualsAndHashCode(callSuper = true)
public class Lead extends BaseEntity {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户姓名
     */
    @Column(nullable = false, length = 128)
    private String name;

    /**
     * 公司名称
     */
    @Column(nullable = false, length = 255)
    private String company;

    /**
     * 所属行业
     */
    @Column(length = 128)
    private String industry;

    /**
     * 联系方式
     */
    @Column(length = 64)
    private String phone;

    /**
     * 线索等级
     * 可选值示例：high / follow / new
     */
    @Column(length = 32)
    private String level;

    /**
     * 当前状态
     * 可选值示例：新线索 / 待跟进 / 待回访 / 已预约 / 已跟进
     */
    @Column(length = 64)
    private String status;

    /**
     * 来源渠道
     * 如：抖音投流 / AI 外呼 / 表单收集 / 公众号
     */
    @Column(length = 128)
    private String source;

    /**
     * 客户标签，使用逗号分隔存储
     * 如：高意向,校区扩张,已回复
     */
    @Column(length = 1000)
    private String tags;

    /**
     * AI 建议或销售备注
     */
    @Column(length = 1000)
    private String note;

    /**
     * 最近跟进时间文案
     * 如：今天 10:20 / 刚刚
     */
    @Column(name = "last_follow", length = 128)
    private String lastFollow;
}
