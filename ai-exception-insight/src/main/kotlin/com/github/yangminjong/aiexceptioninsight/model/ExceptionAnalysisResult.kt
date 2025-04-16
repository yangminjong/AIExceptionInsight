package com.github.yangminjong.aiexceptioninsight.model

/**
 * AI 예외 분석 결과를 담는 데이터 클래스
 */
data class ExceptionAnalysisResult(
    val possibleCause: String,        // 가능성 높은 원인
    val solutionGuide: String,        // 해결 가이드
    val similarCases: List<String>,   // 유사 사례
    val additionalContext: String?,   // 추가 컨텍스트 정보 (선택적)
    val rawAiResponse: String?        // AI 원본 응답 (디버깅용)
) 