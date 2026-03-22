package com.example.leadbot.member;

import com.example.leadbot.common.BusinessException;
import com.example.leadbot.common.OperateLogService;
import com.example.leadbot.member.dto.MemberSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final OperateLogService operateLogService;

    @Transactional(readOnly = true)
    public List<Member> list() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member save(Long id, MemberSaveRequest request) {
        Member member;

        if (id == null) {
            member = new Member();
        } else {
            member = memberRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(404, "成员不存在"));
        }

        member.setName(request.getName());
        member.setRole(request.getRole());
        member.setLeads(request.getLeads());
        member.setAvatar(request.getAvatar());

        Member saved = memberRepository.save(member);

        operateLogService.save(
                "MEMBER",
                id == null ? "CREATE" : "UPDATE",
                "system",
                (id == null ? "创建成员：" : "更新成员：") + saved.getName()
        );

        return saved;
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "成员不存在"));

        memberRepository.delete(member);

        operateLogService.save(
                "MEMBER",
                "DELETE",
                "system",
                "删除成员：" + member.getName()
        );
    }
}
