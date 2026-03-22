package com.example.leadbot.task;

import com.example.leadbot.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务实体
 *
 * 用于任务中心、待办看板等场景。
 */
@Entity
@Table(name = "task_item")
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务标题
     */
    @Column(nullable = false, length = 255)
    private String title;

    /**
     * 任务描述
     */
    @Column(name = "`desc`", length = 255)
    private String desc;

    /**
     * 是否完成
     */
    @Column(nullable = false)
    private Boolean done = false;
}
