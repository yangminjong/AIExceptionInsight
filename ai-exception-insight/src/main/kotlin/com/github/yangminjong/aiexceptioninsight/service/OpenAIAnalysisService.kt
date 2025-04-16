package com.github.yangminjong.aiexceptioninsight.service

import com.github.yangminjong.aiexceptioninsight.model.ExceptionAnalysisResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.MediaType

/**
 * OpenAI API를 사용한 예외 분석 서비스 구현
 */
@Service
class OpenAIAnalysisService(
    private val webClient: WebClient
) : AIAnalysisService {
    
    @Value("\${ai-exception-insight.openai.api-key}")
    private lateinit var apiKey: String
    
    @Value("\${ai-exception-insight.openai.model:gpt-3.5-turbo}")
    private lateinit var model: String
    
    override suspend fun analyzeException(exceptionInfo: String): ExceptionAnalysisResult {
        val prompt = """
            분석해야 할 예외 정보가 아래에 있습니다. 다음 정보를 추출해주세요:
            1. 가능성 높은 원인
            2. 해결 방법 추천
            3. 유사한 문제 사례 (최대 3개)
            
            예외 정보:
            $exceptionInfo
        """.trimIndent()
        
        val requestBody = mapOf(
            "model" to model,
            "messages" to listOf(
                mapOf("role" to "system", "content" to "당신은 Java/Kotlin 애플리케이션 예외를 분석하는 전문가입니다."),
                mapOf("role" to "user", "content" to prompt)
            )
        )
        
        val response = webClient.post()
            .uri("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono<Map<String, Any>>()
            .awaitSingle()
        
        val choices = response["choices"] as List<Map<String, Any>>
        val message = choices[0]["message"] as Map<String, Any>
        val content = message["content"] as String
        
        // 간단한 파싱 로직 (실제로는 더 견고한 구현이 필요)
        val lines = content.split("\n")
        var possibleCause = ""
        var solutionGuide = ""
        val similarCases = mutableListOf<String>()
        
        var currentSection = ""
        
        for (line in lines) {
            when {
                line.contains("가능성 높은 원인") -> currentSection = "cause"
                line.contains("해결 방법") -> currentSection = "solution"
                line.contains("유사한 문제 사례") -> currentSection = "cases"
                else -> {
                    when (currentSection) {
                        "cause" -> possibleCause += "$line\n"
                        "solution" -> solutionGuide += "$line\n"
                        "cases" -> {
                            if (line.trim().isNotEmpty()) {
                                similarCases.add(line)
                            }
                        }
                    }
                }
            }
        }
        
        return ExceptionAnalysisResult(
            possibleCause = possibleCause.trim(),
            solutionGuide = solutionGuide.trim(),
            similarCases = similarCases,
            additionalContext = null,
            rawAiResponse = content
        )
    }
} 