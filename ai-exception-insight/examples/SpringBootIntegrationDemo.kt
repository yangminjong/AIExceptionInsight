package examples

import com.github.yangminjong.aiexceptioninsight.AIExceptionInsight
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring Boot 애플리케이션에 AIExceptionInsight 통합 예제
 */
@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

/**
 * AIExceptionInsight 설정
 */
@Configuration
class AIExceptionInsightConfig {
    
    @Value("\${openai.api-key}")
    private lateinit var apiKey: String
    
    @Bean
    fun aiExceptionInsight(): AIExceptionInsight {
        return AIExceptionInsight.create(apiKey)
    }
}

/**
 * 테스트용 컨트롤러
 */
@RestController
class DemoController {
    
    @GetMapping("/demo/nullpointer")
    fun triggerNullPointer(): String {
        val nullObject: String? = null
        return nullObject!!.length.toString() // NullPointerException 발생
    }
    
    @GetMapping("/demo/arithmetic")
    fun triggerArithmeticException(): Int {
        return 10 / 0 // ArithmeticException 발생
    }
    
    @GetMapping("/demo/indexoutofbounds")
    fun triggerIndexOutOfBounds(): Int {
        val list = listOf(1, 2, 3)
        return list[10] // IndexOutOfBoundsException 발생
    }
}

/**
 * 글로벌 예외 핸들러 - AIExceptionInsight 연동
 */
@RestControllerAdvice
class GlobalExceptionHandler(
    private val aiExceptionInsight: AIExceptionInsight
) {
    
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Map<String, Any>> {
        // AI로 예외 분석
        val analysisResult = aiExceptionInsight.analyze(ex)
        
        // 분석 결과를 응답으로 반환
        val responseBody = mapOf(
            "error" to ex.javaClass.simpleName,
            "message" to (ex.message ?: "Unknown error"),
            "aiAnalysis" to mapOf(
                "possibleCause" to analysisResult.possibleCause,
                "solutionGuide" to analysisResult.solutionGuide,
                "similarCases" to analysisResult.similarCases
            )
        )
        
        return ResponseEntity.status(500).body(responseBody)
    }
} 