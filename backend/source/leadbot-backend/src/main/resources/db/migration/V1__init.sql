CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(64) NOT NULL,
    role VARCHAR(32) NOT NULL,
    avatar VARCHAR(16) DEFAULT '',
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE TABLE lead (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    company VARCHAR(255) NOT NULL,
    industry VARCHAR(128),
    phone VARCHAR(64),
    level VARCHAR(32),
    status VARCHAR(64),
    source VARCHAR(128),
    tags VARCHAR(1000),
    note VARCHAR(1000),
    last_follow VARCHAR(128),
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE INDEX idx_lead_name ON lead(name);
CREATE INDEX idx_lead_company ON lead(company);
CREATE INDEX idx_lead_level ON lead(level);
CREATE INDEX idx_lead_status ON lead(status);

CREATE TABLE follow_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    lead_id BIGINT NOT NULL,
    time VARCHAR(128),
    title VARCHAR(255) NOT NULL,
    `desc` VARCHAR(2000),
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE INDEX idx_follow_log_lead_id ON follow_log(lead_id);

CREATE TABLE task_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    `desc` VARCHAR(255),
    done BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE TABLE chat_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role VARCHAR(32) NOT NULL,
    text VARCHAR(2000) NOT NULL,
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE TABLE team_member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    role VARCHAR(64) NOT NULL,
    leads INT DEFAULT 0,
    avatar VARCHAR(16) DEFAULT '',
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE TABLE channel_setting (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    budget VARCHAR(255),
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE TABLE rule_setting (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    time_range VARCHAR(255),
    follow_rule VARCHAR(1000),
    industry_rule VARCHAR(1000),
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE TABLE operate_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    module VARCHAR(64) NOT NULL,
    action VARCHAR(64) NOT NULL,
    operator_name VARCHAR(64),
    detail VARCHAR(2000),
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE INDEX idx_operate_module ON operate_log(module);
CREATE INDEX idx_operate_action ON operate_log(action);
