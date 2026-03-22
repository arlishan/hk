package com.example.leadbot.lead;

import com.example.leadbot.common.ApiResponse;
import com.example.leadbot.lead.dto.LeadCreateRequest;
import com.example.leadbot.lead.dto.LeadQueryRequest;
import com.example.leadbot.lead.dto.LeadUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 线索管理控制器
 *
 * 对外提供：
 * - 线索分页查询
 * - 新建线索
 * - 更新线索
 * - 删除线索
 */
@RestController
@RequestMapping("/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    /**
     * 分页查询线索列表
     *
     * 支持参数：
     * - keyword: 关键字（姓名/公司）
     * - level: 线索等级
     * - page: 页码，从 1 开始
     * - pageSize: 每页条数
     */
    @GetMapping
    public ApiResponse<?> list(LeadQueryRequest request) {
        return ApiResponse.ok(leadService.list(request));
    }

    /**
     * 新建线索
     */
    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody LeadCreateRequest request) {
        return ApiResponse.ok(leadService.create(request));
    }

    /**
     * 更新线索
     */
    @PatchMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id,
                                 @RequestBody LeadUpdateRequest request) {
        return ApiResponse.ok(leadService.update(id, request));
    }

    /**
     * 删除线索
     */
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        leadService.delete(id);
        return ApiResponse.ok();
    }
}
