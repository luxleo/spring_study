package study.advanced_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import study.advanced_proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import study.advanced_proxy.trace.logtrace.LogTrace;
import study.advanced_proxy.trace.logtrace.ThreadLocalLogTrace;

//@Import(AppConfigV1.class)
//@Import({AppConfigV1.class, AppConfigV2.class})
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
//@Import(DynamicProxyBasicConfiguration.class)
//@Import(DynamicProxyFilterConfiguration.class)
//@Import(ProxyFactoryConfigV1.class)
@Import(ProxyFactoryConfigV2.class)
@SpringBootApplication(scanBasePackages = "study.advanced_proxy.app.v3")
public class AdvancedProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedProxyApplication.class, args);
	}

	@Bean
	public LogTrace trace() {
		return new ThreadLocalLogTrace();
	}

}
