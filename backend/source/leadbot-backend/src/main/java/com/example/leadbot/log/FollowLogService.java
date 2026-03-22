package com.example.leadbot.log;

import com.example.leadbot.common.BusinessException;
import com.example.leadbot.common.OperateLogService;
import com.example.leadbot.log.dto.FollowLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户跟进记录服务
 */
@Service
@RequiredArgsConstructor
public class FollowLogService {

    private final FollowLogRepository followLogRepository;
    private final OperateLogService operateLogService;

    /**
     * 按客户 ID 获取跟进记录，按主键倒序返回
     *
     * @param leadId 客户 ID
     * @return 跟进记录列表
     */
    public List<FollowLog> listByLeadId(Long leadId) {
        if (leadId == null) {
            throw new BusinessException(400, "客户ID不能为空");
        }
        return followLogRepository.findByLeadIdOrderByIdDesc(leadId);
    }

    /**
     * 新增一条跟进记录
     *
     * @param leadId  客户 ID
     * @param request 跟进记录请求体
     * @return 保存后的跟进记录
     */
    @Transactional
    public FollowLog add(Long leadId, FollowLogRequest request) {
        if (leadId == null) {
            throw new BusinessException(400, "客户ID不能为空");
        }
        if (request == null) {
            throw new BusinessException(400, "请求参数不能为空");
        }

        FollowLog log = new FollowLog();
        log.setLeadId(leadId);
        log.setTime("刚刚");
        log.setTitle(request.getTitle());
        log.setDesc(request.getDesc());

        FollowLog saved = followLogRepository.save(log);

        operateLogService.save(
                "FOLLOW_LOG",
                "CREATE",
                "system",
                "新增客户跟进记录，leadId=" + leadId + ", title=" + request.getTitle()
        );

        return saved;
    }
}
