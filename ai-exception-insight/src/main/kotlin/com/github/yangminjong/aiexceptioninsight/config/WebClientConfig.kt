package com.github.yangminjong.aiexceptioninsight.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

/**
 * WebClient 설정
 */
@Configuration
class WebClientConfig {
    
    @Bean
    fun webClient(): WebClient {
        return WebClient.builder().build()
    }
} 