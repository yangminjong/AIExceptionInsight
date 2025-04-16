package com.github.yangminjong.aiexceptioninsight.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ExceptionAnalysisResultTest {
    
    @Test
    fun `분석 결과 객체가 올바르게 생성되는지 테스트`() {
        // given
        val possibleCause = "인덱스 범위 초과"
        val solutionGuide = "리스트의 크기를 확인하고 올바른 인덱스를 사용하세요."
        val similarCases = listOf("ArrayList 접근 오류", "배열 인덱스 범위 초과")
        val additionalContext = "자주 발생하는 문제"
        val rawAiResponse = "AI 원본 응답 내용"
        
        // when
        val result = ExceptionAnalysisResult(
            possibleCause = possibleCause,
            solutionGuide = solutionGuide,
            similarCases = similarCases,
            additionalContext = additionalContext,
            rawAiResponse = rawAiResponse
        )
        
        // then
        assertEquals(possibleCause, result.possibleCause)
        assertEquals(solutionGuide, result.solutionGuide)
        assertEquals(similarCases, result.similarCases)
        assertEquals(additionalContext, result.additionalContext)
        assertEquals(rawAiResponse, result.rawAiResponse)
    }
    
    @Test
    fun `선택적 필드가 null인 경우에도 올바르게 생성되는지 테스트`() {
        // given
        val possibleCause = "인덱스 범위 초과"
        val solutionGuide = "리스트의 크기를 확인하고 올바른 인덱스를 사용하세요."
        val similarCases = listOf("ArrayList 접근 오류", "배열 인덱스 범위 초과")
        
        // when
        val result = ExceptionAnalysisResult(
            possibleCause = possibleCause,
            solutionGuide = solutionGuide,
            similarCases = similarCases,
            additionalContext = null,
            rawAiResponse = null
        )
        
        // then
        assertEquals(possibleCause, result.possibleCause)
        assertEquals(solutionGuide, result.solutionGuide)
        assertEquals(similarCases, result.similarCases)
        assertNull(result.additionalContext)
        assertNull(result.rawAiResponse)
    }
    
    @Test
    fun `빈 리스트의 경우도 올바르게 처리되는지 테스트`() {
        // given
        val possibleCause = "인덱스 범위 초과"
        val solutionGuide = "리스트의 크기를 확인하고 올바른 인덱스를 사용하세요."
        val similarCases = emptyList<String>()
        
        // when
        val result = ExceptionAnalysisResult(
            possibleCause = possibleCause,
            solutionGuide = solutionGuide,
            similarCases = similarCases,
            additionalContext = null,
            rawAiResponse = null
        )
        
        // then
        assertEquals(possibleCause, result.possibleCause)
        assertEquals(solutionGuide, result.solutionGuide)
        assertTrue(result.similarCases.isEmpty())
    }
} 