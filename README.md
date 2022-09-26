# Spring Framework Template for Gradle


## Package Versions

- Java 17
- Gradle 7.5
- Spring boot 2.7.3
- Spring Cloud 2021.0.3
- Spring RestDocs
- Spring Data JPA
- QueryDSL
- WireMock
- Lombok
- MapStruct

## Installing Gradle

https://docs.gradle.org/current/userguide/installation.html

spring boot 2.3.x 부터 gradle 6.x 버전 필요

```
$ ./gradlew wrapper --gradle-version=7.2
```

## 로컬 개발시 spring application properties 설정

스프링 profile 의 기본 설정은 default 이다. 설정 커스텀이 필요할 경우 `application-default.yml` 파일을 만들어 사용하면 됩니다.

> 개인적인 설정으로 application-default.yml 파일은 git commit 에 포함되지 않아야 한다.

## 테스트

> Test 실행인 경우만 IntelliJ 로 설정하는 것을 권장함. 단 CI 에서는 Gradle 을 사용할 것.

### Running IntelliJ tests

- 최초 빌드업 테스트 실행 속도가 빠름. (테스트 처리 시간을 의미하지 않음)
- build.gradle Test Task 사용하지 않는 다.
- Gradle 빌드 결과와 테스트 실행의 결과가 다른 결과를 얻을 수도 있다.


1. IntelliJ IDEA > Build, Execution, Deployment > Build Tools > Gradle

```
Build and run using: Gradle
Run tests using: IntelliJ
```


Junit 실행시 profile 을 기본적으로 test 로 설정될 수 있도록 아래와 같이 작업한다.

1. IntelliJ IDEA > File > New Projects Settings > Run Configuration Templates for New Projects...
2. Junit 를 선택한다.
3. VM option 에 아래와 같이 추가한다.
```
-Dspring.profiles.active=test
```


### Running Gradle tests

- 최초 빌드업 테스트 실행 속도가 느림. (테스트 처리 시간을 의미하지 않음)
- build.gradle Test Task 사용한다.
- Gradle 빌드 결과와 테스트 실행의 결과가 동일한 결과를 얻을 수 있다.

```
IntelliJ IDEA > Build, Execution, Deployment > Build Tools > Gradle 메뉴에서

Build and run using: Gradle
Run tests using: Gradle
```

### 참고 링크

- https://www.jetbrains.com/help/idea/work-with-tests-in-gradle.html#configure_gradle_test_runner