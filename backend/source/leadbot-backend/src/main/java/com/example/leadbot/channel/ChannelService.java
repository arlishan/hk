package com.example.leadbot.channel;

import com.example.leadbot.channel.dto.ChannelUpdateRequest;
import com.example.leadbot.common.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;

    public List<ChannelSetting> list() {
        return channelRepository.findAll();
    }

    public ChannelSetting update(Long id, ChannelUpdateRequest request) {
        ChannelSetting channel = channelRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "渠道不存在"));

        if (request.getName() != null) {
            channel.setName(request.getName());
        }
        if (request.getBudget() != null) {
            channel.setBudget(request.getBudget());
        }

        return channelRepository.save(channel);
    }
}
