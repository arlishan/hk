package com.example.leadbot.common;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "operate_log")
@Data
@EqualsAndHashCode(callSuper = true)
public class OperateLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String module;

    private String action;

    private String operatorName;

    @Column(length = 2000)
    private String detail;
}
