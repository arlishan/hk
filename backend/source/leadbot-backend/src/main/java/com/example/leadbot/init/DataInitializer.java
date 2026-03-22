package com.example.leadbot.init;

import com.example.leadbot.channel.ChannelRepository;
import com.example.leadbot.channel.ChannelSetting;
import com.example.leadbot.enums.UserRoleEnum;
import com.example.leadbot.lead.Lead;
import com.example.leadbot.lead.LeadRepository;
import com.example.leadbot.member.Member;
import com.example.leadbot.member.MemberRepository;
import com.example.leadbot.rule.RuleRepository;
import com.example.leadbot.rule.RuleSetting;
import com.example.leadbot.task.Task;
import com.example.leadbot.task.TaskRepository;
import com.example.leadbot.user.User;
import com.example.leadbot.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LeadRepository leadRepository;
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final RuleRepository ruleRepository;

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (userRepository.count() == 0) {
            userRepository.save(buildUser("admin", encoder.encode("123456"), "系统管理员", UserRoleEnum.ADMIN.getCode(), "A"));
            userRepository.save(buildUser("manager", encoder.encode("123456"), "刘经理", UserRoleEnum.MANAGER.getCode(), "L"));
            userRepository.save(buildUser("user", encoder.encode("123456"), "王专员", UserRoleEnum.USER.getCode(), "W"));
        }

        if (leadRepository.count() == 0) {
            leadRepository.save(buildLead(
                    "张晓峰",
                    "启航教育",
                    "教育培训",
                    "138****2234",
                    "high",
                    "待跟进",
                    "抖音投流",
                    "高意向,校区扩张,已回复",
                    "建议24小时内完成二次跟进，并发送教育行业成功案例。",
                    "今天 10:20"
            ));
            leadRepository.save(buildLead(
                    "刘美琳",
                    "城南口腔",
                    "医疗口腔",
                    "156****9021",
                    "follow",
                    "待回访",
                    "AI 外呼",
                    "门店引流,待报价",
                    "建议补充报价区间与到店转化路径。",
                    "今天 09:10"
            ));
            leadRepository.save(buildLead(
                    "陈志远",
                    "悦享健身",
                    "本地生活",
                    "185****1209",
                    "new",
                    "新线索",
                    "表单收集",
                    "新线索,健身房",
                    "客户处于需求确认阶段，建议低门槛切入。",
                    "今天 08:25"
            ));
        }

        if (taskRepository.count() == 0) {
            taskRepository.save(buildTask("跟进张晓峰，确认演示时间", "优先级：高", false));
            taskRepository.save(buildTask("向城南口腔发送报价方案", "优先级：中", false));
            taskRepository.save(buildTask("复盘本周 AI 外呼数据", "优先级：中", true));
        }

        if (memberRepository.count() == 0) {
            memberRepository.save(buildMember("刘经理", "销售负责人", 48, "L"));
            memberRepository.save(buildMember("王珊", "招商主管", 35, "W"));
            memberRepository.save(buildMember("陈宇", "客户经理", 28, "C"));
            memberRepository.save(buildMember("赵宁", "AI 运营专员", 31, "Z"));
        }

        if (channelRepository.count() == 0) {
            channelRepository.save(buildChannel("抖音投流", "当前预算：¥12,000 / 周"));
            channelRepository.save(buildChannel("公众号留资", "当前预算：¥3,500 / 周"));
            channelRepository.save(buildChannel("AI 外呼渠道", "当前预算：¥5,000 / 周"));
        }

        if (ruleRepository.count() == 0) {
            RuleSetting rule = new RuleSetting();
            rule.setTimeRange("工作日 09:00 - 11:30、14:00 - 18:00");
            rule.setFollowRule("A 类每天；B 类隔天；C 类每周 2 次");
            rule.setIndustryRule("教育优先推演示，本地生活优先推到店引流，医疗优先推咨询转化");
            ruleRepository.save(rule);
        }
    }

    private User buildUser(String username, String password, String name, String role, String avatar) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setRole(role);
        user.setAvatar(avatar);
        return user;
    }

    private Lead buildLead(
            String name,
            String company,
            String industry,
            String phone,
            String level,
            String status,
            String source,
            String tags,
            String note,
            String lastFollow
    ) {
        Lead lead = new Lead();
        lead.setName(name);
        lead.setCompany(company);
        lead.setIndustry(industry);
        lead.setPhone(phone);
        lead.setLevel(level);
        lead.setStatus(status);
        lead.setSource(source);
        lead.setTags(tags);
        lead.setNote(note);
        lead.setLastFollow(lastFollow);
        return lead;
    }

    private Task buildTask(String title, String desc, boolean done) {
        Task task = new Task();
        task.setTitle(title);
        task.setDesc(desc);
        task.setDone(done);
        return task;
    }

    private Member buildMember(String name, String role, Integer leads, String avatar) {
        Member member = new Member();
        member.setName(name);
        member.setRole(role);
        member.setLeads(leads);
        member.setAvatar(avatar);
        return member;
    }

    private ChannelSetting buildChannel(String name, String budget) {
        ChannelSetting channel = new ChannelSetting();
        channel.setName(name);
        channel.setBudget(budget);
        return channel;
    }
}
