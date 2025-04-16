# AIExceptionInsight

이 프로젝트는 Java/Kotlin 애플리케이션에서 발생한 예외(Exception)나 스택트레이스를 AI에게 보내 분석하고, 가장 가능성 높은 원인, 해결책, 유사 사례를 요약해주는 AI 기반의 예외 분석 라이브러리입니다.

## 📋 목차

- [개요](#개요)
- [설치 방법](#설치-방법)
- [사용 방법](#사용-방법)
- [예제](#예제)
- [기능](#기능)
- [License](#license)

## 개요

AIExceptionInsight는 개발자가 애플리케이션에서 발생하는 예외를 빠르게 이해하고 디버깅하는 데 도움을 주는 도구입니다. OpenAI API를 활용하여 예외 내용을 분석하고, 개발자에게 유용한 인사이트를 제공합니다.

### 📍 핵심 기능

- 예외 및 스택트레이스 파싱
- OpenAI Chat API 호출 및 응답 처리
- 문제 원인 요약 + 해결 가이드 제공
- 간단한 API로 try-catch 블록에 연동 가능
- Spring Boot 애플리케이션 통합 가능

## 설치 방법

### Gradle

`build.gradle` 파일에 다음 코드를 추가하세요:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.yangminjong:AIExceptionInsight:latest-version'
}
```

### Maven

`pom.xml` 파일에 다음 코드를 추가하세요:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.yangminjong</groupId>
        <artifactId>AIExceptionInsight</artifactId>
        <version>latest-version</version>
    </dependency>
</dependencies>
```

## 사용 방법

### 기본 사용법

```kotlin
// AIExceptionInsight 인스턴스 생성
val aiInsight = AIExceptionInsight.create("your-openai-api-key")

try {
    // 예외가 발생할 수 있는 코드
    // ...
} catch (e: Exception) {
    // 예외 분석
    val result = aiInsight.analyze(e)
    
    // 분석 결과 활용
    println("가능성 높은 원인: ${result.possibleCause}")
    println("해결 가이드: ${result.solutionGuide}")
    println("유사 사례: ${result.similarCases}")
}
```

### Spring Boot 통합

```kotlin
@Configuration
class AIExceptionInsightConfig {
    
    @Value("\${openai.api-key}")
    private lateinit var apiKey: String
    
    @Bean
    fun aiExceptionInsight(): AIExceptionInsight {
        return AIExceptionInsight.create(apiKey)
    }
}

@RestControllerAdvice
class GlobalExceptionHandler(
    private val aiExceptionInsight: AIExceptionInsight
) {
    
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Map<String, Any>> {
        val analysisResult = aiExceptionInsight.analyze(ex)
        
        // 분석 결과를 응답으로 반환
        // ...
    }
}
```

## 예제

자세한 예제는 [examples 디렉토리](./ai-exception-insight/examples)를 참조하세요:
- [기본 사용 예제](./ai-exception-insight/examples/SimpleDemo.kt)
- [Spring Boot 통합 예제](./ai-exception-insight/examples/SpringBootIntegrationDemo.kt)

## 기능

- **예외 파싱**: 다양한 예외 유형과 스택트레이스를 분석하기 쉬운 형태로 변환
- **AI 분석**: OpenAI API를 통해 예외의 원인과 해결책을 분석
- **유연한 통합**: 독립적인 사용 및 Spring Boot 연동 지원
- **확장 가능**: 다양한 AI 모델과 분석 전략을 지원하도록 설계

## License

MIT License © [Yang Minjong](https://github.com/yangminjong)

---

프로젝트에 기여하거나 이슈를 보고하려면 [GitHub 저장소](https://github.com/yangminjong/AIExceptionInsight)를 방문하세요.
