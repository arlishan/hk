package com.example.leadbot.member;

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
 * 团队成员实体
 *
 * 用于团队成员管理模块，记录成员姓名、岗位、线索数量和头像简写。
 */
@Entity
@Table(name = "team_member")
@Data
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {

    /**
     * 主键 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 成员姓名
     */
    @Column(nullable = false, length = 64)
    private String name;

    /**
     * 成员岗位/角色名称
     */
    @Column(nullable = false, length = 64)
    private String role;

    /**
     * 本周线索数
     */
    @Column(nullable = false)
    private Integer leads = 0;

    /**
     * 头像简写，例如 L / W / C
     */
    @Column(length = 16)
    private String avatar;
}
