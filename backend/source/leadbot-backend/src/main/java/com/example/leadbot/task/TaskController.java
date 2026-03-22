package com.example.leadbot.task;

import com.example.leadbot.common.ApiResponse;
import com.example.leadbot.task.dto.TaskUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 任务管理控制器
 *
 * 提供任务列表查询与任务状态更新接口。
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * 获取任务列表
     *
     * @return 任务列表
     */
    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok(taskService.list());
    }

    /**
     * 更新任务状态
     *
     * @param id      任务ID
     * @param request 更新请求体
     * @return 更新后的任务对象
     */
    @PatchMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id,
                                 @RequestBody TaskUpdateRequest request) {
        return ApiResponse.ok(taskService.update(id, request));
    }
}
