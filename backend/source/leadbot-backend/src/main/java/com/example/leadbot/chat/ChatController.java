package com.example.leadbot.chat;

import com.example.leadbot.chat.dto.ChatSendRequest;
import com.example.leadbot.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok(chatService.list());
    }

    @PostMapping
    public ApiResponse<?> send(@Valid @RequestBody ChatSendRequest request) {
        return ApiResponse.ok(chatService.send(request));
    }
}
