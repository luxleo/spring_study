# Spring bean 수동 등록 vs 자동등록
    수동등록 : @Configuration으로 데코레이팅한 클래스 내부에 @Bean어노테이션으로 등록
    자동등록 : @Component 및 그것이 포함된, @Service, @Controller, @Repository등으로 처리한다.

# Config 선택 적용
    @Configuration 어노테이션은 @Component를 포함하여 자동 스캔 대상이된다.
    
    따라서
    @Import({AppConfigV1.class, AppConfigV2.class})
    @SpringBootApplication(scanBasePackages = "study.advanced_proxy.app.v3")
    을 이용하여 스프링부트의 컴포넌트 자동 스캔 범위를 제한한 후에
    @Import로 적용할 @Configuration을 적용한다.
[참조파일](./src/main/java/study/advanced_proxy/AdvancedProxyApplication.java)

# Proxy
    client 와 서버 사이에서 작동하는 중재자이다.
    클라이언트는 서버와 통신 하는지 프록시를 거쳐서 서버와 통신하는지 알 필요도 없고 알 수 없다.
    프록시를 사용하며 얻는 두 가지 기능은 접근제어(캐싱, 권한에 따른 접근 차단, 지연 로딩)과
    부가 기능( 요청값 또는 응답 값을 중간에서 변경, 실행 시간 측정하여 로그 남김)추가 이다.

# Proxy pattern
    프록시 패턴의 의도(intent)는 프록시의 두 가지 기능중 접근제어를 달성하기 위함이다.
    프록시 패턴이 호출하는 로직을 target이라고 한다.
# Decorator Pattern
    데코레이터 패턴의 의도(indent)는 프록시의 두 가지 기능중 부가 기능 추가를 달성하기 위함이다.
# proxy chaining
    프록시는 연쇄적으로 다른 프록시를 호출할 수 있다.
# 인터페이스 기반 vs 클래스 기반
    클래스 기반 -> 해당 클래스에만 적용이 가능하다, 
    인터페이스 기반 -> 모든 점에서 클래스기반 프록시 보다 앞서지만, 인터페이스가 필요하다는 단점이 있다.
    해결하는 방법은 동적프록시를 이용하는 것이다.