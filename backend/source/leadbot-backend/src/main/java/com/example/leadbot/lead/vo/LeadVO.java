package com.example.leadbot.lead.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 线索视图对象
 *
 * 用于返回给前端展示的客户线索数据。
 * 相比实体对象，VO 更适合做：
 * 1. 字段裁剪
 * 2. 展示结构整理
 * 3. 后续扩展聚合字段
 */
@Data
public class LeadVO {

    /**
     * 主键ID
     */
    private Long id;

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
     * 线索等级：high / follow / new
     */
    private String level;

    /**
     * 当前状态，如：新线索、待跟进、已预约、已跟进
     */
    private String status;

    /**
     * 来源渠道
     */
    private String source;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * AI 跟进建议 / 备注
     */
    private String note;

    /**
     * 最近跟进时间（展示文本）
     */
    private String lastFollow;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
