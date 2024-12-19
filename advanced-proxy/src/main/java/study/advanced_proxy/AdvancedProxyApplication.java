package study.advanced_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import study.advanced_proxy.config.v1_proxy.concrete_proxy.ConcreteProxyConfig;
import study.advanced_proxy.trace.logtrace.LogTrace;
import study.advanced_proxy.trace.logtrace.ThreadLocalLogTrace;

//@Import(AppConfigV1.class)
//@Import({AppConfigV1.class, AppConfigV2.class})
//@Import(InterfaceProxyConfig.class)
@Import(ConcreteProxyConfig.class)
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
