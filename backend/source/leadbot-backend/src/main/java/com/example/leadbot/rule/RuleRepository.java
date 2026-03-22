package com.example.leadbot.rule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<RuleSetting, Long> {
}
