package com.example.leadbot.rule;

import com.example.leadbot.rule.dto.RuleUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    /**
     * 获取唯一一条规则配置
     * 若数据库中尚未初始化，则创建默认配置并返回
     */
    @Transactional
    public RuleSetting getOne() {
        return ruleRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    RuleSetting rule = new RuleSetting();
                    rule.setTimeRange("工作日 09:00 - 11:30、14:00 - 18:00");
                    rule.setFollowRule("A 类每天；B 类隔天；C 类每周 2 次");
                    rule.setIndustryRule("教育优先推演示，本地生活优先推到店引流，医疗优先推咨询转化");
                    return ruleRepository.save(rule);
                });
    }

    /**
     * 更新规则配置
     */
    @Transactional
    public RuleSetting update(RuleUpdateRequest request) {
        RuleSetting rule = getOne();

        if (request.getTimeRange() != null) {
            rule.setTimeRange(request.getTimeRange());
        }
        if (request.getFollowRule() != null) {
            rule.setFollowRule(request.getFollowRule());
        }
        if (request.getIndustryRule() != null) {
            rule.setIndustryRule(request.getIndustryRule());
        }

        return ruleRepository.save(rule);
    }
}
