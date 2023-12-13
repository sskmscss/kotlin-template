package com.service.configuration

import com.service.interceptor.ApiKeyVerificationInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ApiKeyConfiguration(
    @Value("\${service.security.api-key}") private val apiKey: String
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(ApiKeyVerificationInterceptor(apiKey))
            .excludePathPatterns("/ah/merchandising/template/v1/swagger/**")
    }
}
