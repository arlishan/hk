package com.example.leadbot.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct 全局配置
 *
 * 说明：
 * 1. 统一项目内 Mapper 的公共行为
 * 2. 未映射字段默认忽略，减少样板代码告警
 * 3. 适合当前 DTO / VO / Entity 的渐进式映射场景
 */
@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MapStructConfig {
}
