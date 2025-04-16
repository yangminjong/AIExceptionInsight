package com.github.yangminjong.aiexceptioninsight.parser

import org.springframework.stereotype.Component
import java.io.PrintWriter
import java.io.StringWriter

/**
 * 기본 예외 파싱 구현체
 */
@Component
class DefaultExceptionParser : ExceptionParser {
    
    override fun parseException(exception: Throwable): String {
        val writer = StringWriter()
        exception.printStackTrace(PrintWriter(writer))
        return buildString {
            appendLine("Exception Type: ${exception.javaClass.name}")
            appendLine("Message: ${exception.message ?: "No message"}")
            appendLine("Stack Trace:")
            append(writer.toString())
        }
    }
    
    override fun parseStackTrace(stackTrace: Array<StackTraceElement>): String {
        return buildString {
            appendLine("Stack Trace:")
            stackTrace.forEach { element ->
                appendLine("\tat $element")
            }
        }
    }
} 