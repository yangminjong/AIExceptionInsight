package com.github.yangminjong.aiexceptioninsight.service

import com.github.yangminjong.aiexceptioninsight.model.ExceptionAnalysisResult

/**
 * AI 분석 서비스 인터페이스
 */
interface AIAnalysisService {
    /**
     * 예외 정보를 AI로 분석하여 결과 반환
     * @param exceptionInfo 분석할 예외 정보 문자열
     * @return 예외 분석 결과
     */
    suspend fun analyzeException(exceptionInfo: String): ExceptionAnalysisResult
} 