package examples

import com.github.yangminjong.aiexceptioninsight.AIExceptionInsight

/**
 * AIExceptionInsight 라이브러리 사용 예제
 */
fun main() {
    // API 키 설정 (실제 사용 시 환경 변수나 설정 파일에서 로드하는 것이 좋습니다)
    val apiKey = "your-openai-api-key-here"
    
    // AIExceptionInsight 인스턴스 생성
    val aiInsight = AIExceptionInsight.create(apiKey)
    
    try {
        // 오류를 발생시키는 코드
        val list = listOf(1, 2, 3)
        val element = list[10] // IndexOutOfBoundsException 발생
        println("이 코드는 실행되지 않습니다: $element")
    } catch (e: Exception) {
        println("예외 발생: ${e.message}")
        println("AI 분석 시작...")
        
        // 예외 분석
        val result = aiInsight.analyze(e)
        
        // 분석 결과 출력
        println("\n=== AI 분석 결과 ===")
        println("가능성 높은 원인:")
        println(result.possibleCause)
        println("\n해결 가이드:")
        println(result.solutionGuide)
        println("\n유사 사례:")
        result.similarCases.forEachIndexed { index, case ->
            println("${index + 1}. $case")
        }
    }
} 