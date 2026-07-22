package project.mqtt.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@OpenAPIDefinition(
        info = @Info(
                title = "TEST API 명세서",
                description = "MQTT 테스트용",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Token");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                .addSecurityItem(securityRequirement);
    }

    @Bean
    public OpenApiCustomizer swaggerTagOrderCustomizer() {
        return openApi -> {
            if (openApi.getTags() != null) {
                List<String> order = Arrays.asList(
                        "로그인",
                        "인증"
                );

                List<Tag> sortedTags = openApi.getTags().stream()
                        .sorted(Comparator.comparingInt(tag ->
                                order.contains(tag.getName()) ? order.indexOf(tag.getName()) : Integer.MAX_VALUE
                        ))
                        .collect(Collectors.toList());

                openApi.setTags(sortedTags);
            }
        };
    }
}
