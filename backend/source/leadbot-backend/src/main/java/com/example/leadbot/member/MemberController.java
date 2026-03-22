package com.example.leadbot.member;

import com.example.leadbot.common.ApiResponse;
import com.example.leadbot.member.dto.MemberSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok(memberService.list());
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody MemberSaveRequest request) {
        return ApiResponse.ok(memberService.save(null, request));
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody MemberSaveRequest request) {
        return ApiResponse.ok(memberService.save(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ApiResponse.ok();
    }
}
