package com.example.leadbot.member.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 团队成员新增/编辑请求 DTO
 */
@Data
public class MemberSaveRequest {

    @NotBlank(message = "成员姓名不能为空")
    @Size(max = 64, message = "成员姓名长度不能超过64个字符")
    private String name;

    @NotBlank(message = "岗位不能为空")
    @Size(max = 64, message = "岗位长度不能超过64个字符")
    private String role;

    @Min(value = 0, message = "线索数不能小于0")
    private Integer leads;

    @Size(max = 16, message = "头像标识长度不能超过16个字符")
    private String avatar;
}
