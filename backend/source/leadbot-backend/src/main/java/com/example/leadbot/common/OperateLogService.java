package com.example.leadbot.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日志服务
 *
 * 用于统一记录系统中的关键操作行为，例如：
 * - 登录
 * - 修改密码
 * - 创建/更新/删除线索
 * - 配置规则
 * - 更新渠道预算
 */
@Service
@RequiredArgsConstructor
public class OperateLogService {

    private final OperateLogRepository operateLogRepository;

    /**
     * 保存一条操作日志
     *
     * @param module       模块名称，如 AUTH / LEAD / TASK / RULE
     * @param action       操作类型，如 LOGIN / CREATE / UPDATE / DELETE
     * @param operatorName 操作人名称
     * @param detail       操作详情
     */
    @Transactional
    public void save(String module, String action, String operatorName, String detail) {
        OperateLog log = new OperateLog();
        log.setModule(module);
        log.setAction(action);
        log.setOperatorName(operatorName);
        log.setDetail(detail);
        operateLogRepository.save(log);
    }
}
