package com.example.leadbot.lead;

import com.example.leadbot.common.BusinessException;
import com.example.leadbot.common.OperateLogService;
import com.example.leadbot.common.PageResponse;
import com.example.leadbot.enums.LeadLevelEnum;
import com.example.leadbot.enums.LeadStatusEnum;
import com.example.leadbot.lead.dto.LeadCreateRequest;
import com.example.leadbot.lead.dto.LeadQueryRequest;
import com.example.leadbot.lead.dto.LeadUpdateRequest;
import com.example.leadbot.lead.mapper.LeadMapper;
import com.example.leadbot.lead.vo.LeadVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;
    private final LeadMapper leadMapper;
    private final OperateLogService operateLogService;

    @Transactional(readOnly = true)
    public PageResponse<LeadVO> list(LeadQueryRequest request) {
        int page = request.getPage() == null || request.getPage() < 1 ? 1 : request.getPage();
        int pageSize = request.getPageSize() == null || request.getPageSize() < 1 ? 10 : request.getPageSize();

        Pageable pageable = PageRequest.of(
                page - 1,
                pageSize,
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<Lead> result;

        boolean hasKeyword = request.getKeyword() != null && !request.getKeyword().isBlank();
        boolean hasLevel = request.getLevel() != null && !request.getLevel().isBlank() && !"all".equalsIgnoreCase(request.getLevel());

        if (hasKeyword && hasLevel) {
            result = leadRepository.findByLevelAndNameContainingIgnoreCaseOrLevelAndCompanyContainingIgnoreCase(
                    request.getLevel(),
                    request.getKeyword(),
                    request.getLevel(),
                    request.getKeyword(),
                    pageable
            );
        } else if (hasKeyword) {
            result = leadRepository.findByNameContainingIgnoreCaseOrCompanyContainingIgnoreCase(
                    request.getKeyword(),
                    request.getKeyword(),
                    pageable
            );
        } else if (hasLevel) {
            result = leadRepository.findByLevel(request.getLevel(), pageable);
        } else {
            result = leadRepository.findAll(pageable);
        }

        return new PageResponse<>(
                leadMapper.toVOList(result.getContent()),
                result.getTotalElements(),
                page,
                pageSize
        );
    }

    @Transactional
    public LeadVO create(LeadCreateRequest request) {
        Lead lead = new Lead();
        lead.setName(request.getName());
        lead.setCompany(request.getCompany());
        lead.setIndustry(request.getIndustry());
        lead.setPhone(request.getPhone());
        lead.setLevel(LeadLevelEnum.NEW.getCode());
        lead.setStatus(LeadStatusEnum.NEW.getLabel());
        lead.setSource("手动录入");
        lead.setTags("新线索,手动录入");
        lead.setNote("建议先进行首次触达，确认客户需求和预算区间。");
        lead.setLastFollow("刚刚");

        Lead saved = leadRepository.save(lead);
        operateLogService.save("LEAD", "CREATE", "system", "创建客户：" + saved.getName());

        return leadMapper.toVO(saved);
    }

    @Transactional
    public LeadVO update(Long id, LeadUpdateRequest request) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "客户不存在"));

        if (request.getName() != null) {
            lead.setName(request.getName());
        }
        if (request.getCompany() != null) {
            lead.setCompany(request.getCompany());
        }
        if (request.getIndustry() != null) {
            lead.setIndustry(request.getIndustry());
        }
        if (request.getPhone() != null) {
            lead.setPhone(request.getPhone());
        }
        if (request.getStatus() != null) {
            lead.setStatus(request.getStatus());
        }
        if (request.getLastFollow() != null) {
            lead.setLastFollow(request.getLastFollow());
        }

        Lead saved = leadRepository.save(lead);
        operateLogService.save("LEAD", "UPDATE", "system", "更新客户：" + saved.getName());

        return leadMapper.toVO(saved);
    }

    @Transactional
    public void delete(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "客户不存在"));

        leadRepository.delete(lead);
        operateLogService.save("LEAD", "DELETE", "system", "删除客户：" + lead.getName());
    }
}
