USE leadbot;

SET NAMES utf8mb4;

DELETE FROM operate_log;
DELETE FROM rule_setting;
DELETE FROM channel_setting;
DELETE FROM team_member;
DELETE FROM chat_message;
DELETE FROM task_item;
DELETE FROM follow_log;
DELETE FROM lead;
DELETE FROM sys_user;

INSERT INTO sys_user (username, password, name, role, avatar)
VALUES
('admin', '$2a$10$7EqJtq98hPqEX7fNZaFWoOa5l8Q0xYz6iYxgUu7N0rGUNShUMHbOW', '系统管理员', 'ADMIN', 'A'),
('manager', '$2a$10$7EqJtq98hPqEX7fNZaFWoOa5l8Q0xYz6iYxgUu7N0rGUNShUMHbOW', '刘经理', 'MANAGER', 'L'),
('user', '$2a$10$7EqJtq98hPqEX7fNZaFWoOa5l8Q0xYz6iYxgUu7N0rGUNShUMHbOW', '王专员', 'USER', 'W');

INSERT INTO lead (name, company, industry, phone, level, status, source, tags, note, last_follow)
VALUES
('张晓峰', '启航教育', '教育培训', '138****2234', 'high', '待跟进', '抖音投流', '高意向,校区扩张,已回复', '建议24小时内完成二次跟进，并发送教育行业成功案例。', '今天 10:20'),
('刘美琳', '城南口腔', '医疗口腔', '156****9021', 'follow', '待回访', 'AI 外呼', '门店引流,待报价', '建议补充报价区间与到店转化路径。', '今天 09:10'),
('陈志远', '悦享健身', '本地生活', '185****1209', 'new', '新线索', '表单收集', '新线索,健身房', '客户处于需求确认阶段，建议低门槛切入。', '今天 08:25'),
('王静', '创星留学', '教育咨询', '139****7788', 'high', '已预约', '公众号', '高意向,已预约演示', '客户对 AI 首咨分流非常感兴趣，建议重点展示转化效率提升部分。', '昨天 18:40');

INSERT INTO follow_log (lead_id, time, title, `desc`)
VALUES
(1, '今天 10:20', '客户回复', '客户已回复并表达对招生转化方案的兴趣。'),
(1, '昨天 15:10', 'AI 触达成功', '机器人完成首次触达并引导留下联系方式。'),
(2, '今天 09:10', '待回访', '客户希望获取报价与实施周期说明。'),
(4, '昨天 18:40', '已预约演示', '客户确认下周三下午进行线上演示。');

INSERT INTO task_item (title, `desc`, done)
VALUES
('跟进张晓峰，确认演示时间', '优先级：高', 0),
('向城南口腔发送报价方案', '优先级：中', 0),
('复盘本周 AI 外呼数据', '优先级：中', 1),
('整理新增行业话术模板', '优先级：低', 0);

INSERT INTO chat_message (role, text)
VALUES
('bot', '你好，我可以根据你的客户画像，自动生成获客话术、跟进建议和成交推进策略。');

INSERT INTO team_member (name, role, leads, avatar)
VALUES
('刘经理', '销售负责人', 48, 'L'),
('王珊', '招商主管', 35, 'W'),
('陈宇', '客户经理', 28, 'C'),
('赵宁', 'AI 运营专员', 31, 'Z');

INSERT INTO channel_setting (name, budget)
VALUES
('抖音投流', '当前预算：¥12,000 / 周'),
('公众号留资', '当前预算：¥3,500 / 周'),
('AI 外呼渠道', '当前预算：¥5,000 / 周');

INSERT INTO rule_setting (time_range, follow_rule, industry_rule)
VALUES
('工作日 09:00 - 11:30、14:00 - 18:00',
 'A 类每天；B 类隔天；C 类每周 2 次',
 '教育优先推演示，本地生活优先推到店引流，医疗优先推咨询转化');

INSERT INTO operate_log (module, action, operator_name, detail)
VALUES
('AUTH', 'LOGIN', '系统管理员', '管理员首次登录系统'),
('LEAD', 'CREATE', '系统管理员', '初始化导入演示线索数据'),
('TASK', 'INIT', '系统管理员', '初始化任务数据'),
('RULE', 'INIT', '系统管理员', '初始化机器人规则配置');
