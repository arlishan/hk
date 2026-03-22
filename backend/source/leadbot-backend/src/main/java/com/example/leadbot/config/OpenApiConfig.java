package com.example.leadbot.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger 配置
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI leadBotOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LeadBot API")
                        .description("AI 智能获客机器人系统后端接口文档")
                        .version("1.2.0")
                        .contact(new Contact()
                                .name("LeadBot Team")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("项目说明文档")
                        .url("https://example.com/leadbot/docs"));
    }
}
