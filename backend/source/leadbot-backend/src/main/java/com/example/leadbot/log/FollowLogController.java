package com.example.leadbot.log;

import com.example.leadbot.common.ApiResponse;
import com.example.leadbot.log.dto.FollowLogRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leads/{leadId}/logs")
@RequiredArgsConstructor
public class FollowLogController {

    private final FollowLogService followLogService;

    @GetMapping
    public ApiResponse<?> list(@PathVariable Long leadId) {
        return ApiResponse.ok(followLogService.listByLeadId(leadId));
    }

    @PostMapping
    public ApiResponse<?> add(@PathVariable Long leadId,
                              @Valid @RequestBody FollowLogRequest request) {
        return ApiResponse.ok(followLogService.add(leadId, request));
    }
}
