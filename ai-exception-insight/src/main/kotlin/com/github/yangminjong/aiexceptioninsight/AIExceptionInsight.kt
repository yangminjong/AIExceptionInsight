package com.github.yangminjong.aiexceptioninsight

import com.github.yangminjong.aiexceptioninsight.model.ExceptionAnalysisResult
import com.github.yangminjong.aiexceptioninsight.parser.ExceptionParser
import com.github.yangminjong.aiexceptioninsight.service.AIAnalysisService
import kotlinx.coroutines.runBlocking
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * AIExceptionInsight 메인 클래스
 * 예외 분석을 위한 메인 진입점
 */
class AIExceptionInsight private constructor(
    private val exceptionParser: ExceptionParser,
    private val aiAnalysisService: AIAnalysisService
) {
    
    /**
     * 예외를 분석하여 결과 반환
     * @param exception 분석할 예외
     * @return 예외 분석 결과
     */
    fun analyze(exception: Throwable): ExceptionAnalysisResult = runBlocking {
        val parsedInfo = exceptionParser.parseException(exception)
        aiAnalysisService.analyzeException(parsedInfo)
    }
    
    /**
     * 스택트레이스를 분석하여 결과 반환
     * @param stackTrace 분석할 스택트레이스
     * @return 예외 분석 결과
     */
    fun analyzeStackTrace(stackTrace: Array<StackTraceElement>): ExceptionAnalysisResult = runBlocking {
        val parsedInfo = exceptionParser.parseStackTrace(stackTrace)
        aiAnalysisService.analyzeException(parsedInfo)
    }
    
    companion object {
        private var instance: AIExceptionInsight? = null
        private lateinit var applicationContext: ApplicationContext
        
        /**
         * AIExceptionInsight 인스턴스 생성
         * 
         * @param apiKey OpenAI API 키
         * @param model 사용할 OpenAI 모델 (기본값: gpt-3.5-turbo)
         * @return AIExceptionInsight 인스턴스
         */
        @JvmStatic
        fun create(apiKey: String, model: String = "gpt-3.5-turbo"): AIExceptionInsight {
            // Spring ApplicationContext 설정
            System.setProperty("ai-exception-insight.openai.api-key", apiKey)
            System.setProperty("ai-exception-insight.openai.model", model)
            
            applicationContext = AnnotationConfigApplicationContext("com.github.yangminjong.aiexceptioninsight")
            
            return instance ?: synchronized(this) {
                instance ?: AIExceptionInsight(
                    applicationContext.getBean(ExceptionParser::class.java),
                    applicationContext.getBean(AIAnalysisService::class.java)
                ).also { instance = it }
            }
        }
    }
} 