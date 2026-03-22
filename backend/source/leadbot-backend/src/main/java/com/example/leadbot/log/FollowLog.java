package com.example.leadbot.log;

import com.example.leadbot.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户跟进记录实体
 *
 * 用于记录某个线索客户的每一次销售跟进动作，例如：
 * - 电话跟进
 * - 微信回复
 * - 预约演示
 * - 报价发送
 * - 回访记录
 */
@Entity
@Table(name = "follow_log")
@Data
@EqualsAndHashCode(callSuper = true)
public class FollowLog extends BaseEntity {

    /**
     * 主键 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属线索 ID
     */
    @Column(name = "lead_id", nullable = false)
    private Long leadId;

    /**
     * 前端展示用时间文案
     * 例如：今天 10:20 / 刚刚 / 昨天 18:40
     */
    @Column(name = "time", length = 128)
    private String time;

    /**
     * 跟进标题
     * 例如：客户回复 / 电话跟进 / 已预约演示
     */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /**
     * 跟进详细内容
     */
    @Column(name = "desc", length = 2000)
    private String desc;
}
