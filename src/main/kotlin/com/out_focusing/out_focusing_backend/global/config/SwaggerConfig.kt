package com.out_focusing.out_focusing_backend.global.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    companion object {
        private const val apiTitle: String = "Out Focusing API"
        private const val apiVersion: String = "0.0.1"
        private const val apiDescription: String = "Out Focusing API Documentation"
    }

    val info = Info().apply {
        title = apiTitle
        description = apiDescription
        version = apiVersion
    }

    @Bean
    fun v1Api(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("Out Focusing")
            .pathsToMatch("/v1/**")
            .build()

    @Bean
    fun outFocusingApi(): OpenAPI = OpenAPI().info(info)
}