package com.example.leadbot.chat;

import com.example.leadbot.chat.dto.ChatSendRequest;
import com.example.leadbot.common.BusinessException;
import com.example.leadbot.common.OperateLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final OperateLogService operateLogService;

    public List<ChatMessage> list() {
        return chatRepository.findAll();
    }

    @Transactional
    public Map<String, String> send(ChatSendRequest request, String operatorName) {
        if (request == null || request.getText() == null || request.getText().isBlank()) {
            throw new BusinessException(400, "消息内容不能为空");
        }

        String text = request.getText().trim();

        ChatMessage userMsg = new ChatMessage();
        userMsg.setRole("user");
        userMsg.setText(text);
        chatRepository.save(userMsg);

        String reply = generateReply(text);

        ChatMessage botMsg = new ChatMessage();
        botMsg.setRole("bot");
        botMsg.setText(reply);
        chatRepository.save(botMsg);

        operateLogService.save(
                "CHAT",
                "SEND",
                operatorName == null || operatorName.isBlank() ? "system" : operatorName,
                "发送 AI 对话消息：" + text
        );

        return Map.of("reply", reply);
    }

    private String generateReply(String text) {
        if (text.contains("教育")) {
            return "教育行业推荐话术：您好，我们专注帮助教育机构提升首咨转化和私域留资效率，方便了解一下您当前的招生渠道与转化瓶颈吗？";
        }
        if (text.contains("跟进")) {
            return "高意向客户建议：首次回复后 24 小时内二次触达，补充案例、试用策略和明确下一步动作。";
        }
        if (text.contains("分析")) {
            return "本周转化问题分析：当前触达量充足，但从有效回复到高意向的转化链路偏弱，建议优化首次文案与二次跟进节奏。";
        }
        if (text.contains("口腔")) {
            return "口腔门店话术建议：您好，我们帮助口腔门店通过 AI 自动触达和私域承接提升到店咨询率，是否方便交流一下您目前的拉新方式？";
        }
        if (text.contains("邀约")) {
            return "邀约建议：可以先建立信任，再给出明确收益点，最后提供低门槛行动，例如“安排 15 分钟在线演示”。";
        }
        return "建议先补充客户行业、规模、当前获客渠道和核心问题，我可以继续为你生成更精准的方案。";
    }
}
