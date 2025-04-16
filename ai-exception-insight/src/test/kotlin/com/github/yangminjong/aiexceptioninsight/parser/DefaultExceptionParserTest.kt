package com.github.yangminjong.aiexceptioninsight.parser

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.io.IOException

class DefaultExceptionParserTest {
    
    private val parser = DefaultExceptionParser()
    
    @Test
    fun `예외 객체를 올바르게 파싱해야 함`() {
        // given
        val exception = RuntimeException("테스트 예외 메시지")
        
        // when
        val result = parser.parseException(exception)
        
        // then
        assertTrue(result.contains("Exception Type: java.lang.RuntimeException"))
        assertTrue(result.contains("Message: 테스트 예외 메시지"))
        assertTrue(result.contains("Stack Trace:"))
    }
    
    @Test
    fun `중첩된 예외도 올바르게 파싱해야 함`() {
        // given
        val cause = IOException("원인 예외")
        val exception = RuntimeException("상위 예외", cause)
        
        // when
        val result = parser.parseException(exception)
        
        // then
        assertTrue(result.contains("Exception Type: java.lang.RuntimeException"))
        assertTrue(result.contains("Message: 상위 예외"))
        assertTrue(result.contains("Caused by: java.io.IOException: 원인 예외"))
    }
    
    @Test
    fun `null 메시지도 처리해야 함`() {
        // given
        val exception = RuntimeException(null as String?)
        
        // when
        val result = parser.parseException(exception)
        
        // then
        assertTrue(result.contains("Exception Type: java.lang.RuntimeException"))
        assertTrue(result.contains("Message: No message"))
    }
    
    @Test
    fun `스택트레이스 파싱이 올바르게 동작해야 함`() {
        // given
        val stackTrace = Thread.currentThread().stackTrace
        
        // when
        val result = parser.parseStackTrace(stackTrace)
        
        // then
        assertTrue(result.contains("Stack Trace:"))
        assertTrue(result.contains("at com.github.yangminjong.aiexceptioninsight.parser.DefaultExceptionParserTest"))
    }
} 