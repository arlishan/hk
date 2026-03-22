package com.example.leadbot.channel;

import com.example.leadbot.channel.dto.ChannelUpdateRequest;
import com.example.leadbot.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 渠道设置控制器
 *
 * 权限说明：
 * - ADMIN / MANAGER 可查看与更新渠道配置
 */
@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    /**
     * 获取渠道配置列表
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ApiResponse<?> list() {
        return ApiResponse.ok(channelService.list());
    }

    /**
     * 更新指定渠道配置
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ApiResponse<?> update(@PathVariable Long id,
                                 @RequestBody ChannelUpdateRequest request) {
        return ApiResponse.ok(channelService.update(id, request));
    }
}
