package com.github.yangminjong.aiexceptioninsight.demo

import com.github.yangminjong.aiexceptioninsight.AIExceptionInsight
import java.io.File
import java.io.FileNotFoundException
import java.util.Scanner

/**
 * AI 예외 분석 테스트 애플리케이션
 */
fun main() {
    println("=== AI 예외 분석 테스트 앱 ===")
    
    // OpenAI API 키를 환경변수 또는 직접 입력받습니다
    val apiKey = System.getenv("OPENAI_API_KEY") ?: readApiKey()
    
    println("AI 예외 분석 엔진 초기화 중...")
    val aiInsight = AIExceptionInsight.create(apiKey)
    println("초기화 완료!")
    
    // 다양한 예외 테스트
    testExceptions(aiInsight)
}

/**
 * API 키를 입력받는 함수
 */
fun readApiKey(): String {
    println("OpenAI API 키를 입력하세요:")
    return Scanner(System.`in`).nextLine().trim()
}

/**
 * 다양한 예외를 테스트하는 함수
 */
fun testExceptions(aiInsight: AIExceptionInsight) {
    println("\n1. NullPointerException 테스트")
    try {
        val str: String? = null
        println(str!!.length)
    } catch (e: Exception) {
        analyzeException(aiInsight, e)
    }
    
    println("\n2. FileNotFoundException 테스트")
    try {
        val file = File("존재하지_않는_파일.txt")
        Scanner(file)
    } catch (e: Exception) {
        analyzeException(aiInsight, e)
    }
    
    println("\n3. ArrayIndexOutOfBoundsException 테스트")
    try {
        val array = intArrayOf(1, 2, 3)
        println(array[10])
    } catch (e: Exception) {
        analyzeException(aiInsight, e)
    }
    
    println("\n4. ArithmeticException 테스트")
    try {
        val result = 10 / 0
        println(result)
    } catch (e: Exception) {
        analyzeException(aiInsight, e)
    }
}

/**
 * 예외를 분석하고 결과를 출력하는 함수
 */
fun analyzeException(aiInsight: AIExceptionInsight, exception: Exception) {
    println("예외 발생: ${exception.javaClass.simpleName} - ${exception.message}")
    println("AI 분석 시작...")
    
    try {
        val result = aiInsight.analyze(exception)
        
        println("\n=== AI 분석 결과 ===")
        println("■ 가능성 높은 원인:")
        println(result.possibleCause)
        
        println("\n■ 해결 가이드:")
        println(result.solutionGuide)
        
        println("\n■ 유사 사례:")
        result.similarCases.forEachIndexed { index, case ->
            println("${index + 1}. $case")
        }
    } catch (e: Exception) {
        println("AI 분석 중 오류 발생: ${e.message}")
        e.printStackTrace()
    }
} 