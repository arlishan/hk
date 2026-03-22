package com.example.leadbot.rule;

import com.example.leadbot.common.ApiResponse;
import com.example.leadbot.rule.dto.RuleUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ApiResponse<?> getOne() {
        return ApiResponse.ok(ruleService.getOne());
    }

    @PatchMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ApiResponse<?> update(@RequestBody RuleUpdateRequest request) {
        return ApiResponse.ok(ruleService.update(request));
    }
}
