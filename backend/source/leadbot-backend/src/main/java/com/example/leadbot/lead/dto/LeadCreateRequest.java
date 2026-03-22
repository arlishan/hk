package com.example.leadbot.lead.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新建线索请求 DTO
 */
@Data
public class LeadCreateRequest {

    @NotBlank(message = "客户姓名不能为空")
    private String name;

    @NotBlank(message = "公司名称不能为空")
    private String company;

    private String industry;

    private String phone;
}
