package com.example.leadbot.lead.dto;

import lombok.Data;

/**
 * 线索列表查询请求对象
 *
 * 用于接收前端分页、关键字和等级筛选参数。
 */
@Data
public class LeadQueryRequest {

    /**
     * 搜索关键字，可匹配客户姓名或公司名称
     */
    private String keyword;

    /**
     * 线索等级，可选值：
     * high / follow / new
     */
    private String level;

    /**
     * 当前页码，从 1 开始
     */
    private Integer page = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;
}
