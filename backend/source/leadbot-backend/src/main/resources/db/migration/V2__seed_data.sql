INSERT INTO task_item(title, `desc`, done, created_at, updated_at)
VALUES
('跟进张晓峰，确认演示时间', '优先级：高', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('向城南口腔发送报价方案', '优先级：中', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('复盘本周 AI 外呼数据', '优先级：中', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO channel_setting(name, budget, created_at, updated_at)
VALUES
('抖音投流', '当前预算：¥12,000 / 周', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('公众号留资', '当前预算：¥3,500 / 周', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO rule_setting(time_range, follow_rule, industry_rule, created_at, updated_at)
VALUES
(
  '工作日 09:00 - 11:30、14:00 - 18:00',
  'A 类每天；B 类隔天；C 类每周 2 次',
  '教育优先推演示，本地生活优先推到店引流，医疗优先推咨询转化',
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
);
