package com.github.yangminjong.aiexceptioninsight.parser

/**
 * 예외 정보를 파싱하는 인터페이스
 */
interface ExceptionParser {
    /**
     * 예외 객체를 파싱하여 문자열로 변환
     * @param exception 파싱할 예외 객체
     * @return 파싱된 예외 정보 문자열
     */
    fun parseException(exception: Throwable): String
    
    /**
     * 스택트레이스를 파싱하여 문자열로 변환
     * @param stackTrace 파싱할 스택트레이스
     * @return 파싱된 스택트레이스 정보 문자열
     */
    fun parseStackTrace(stackTrace: Array<StackTraceElement>): String
} 