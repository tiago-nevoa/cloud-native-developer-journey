package com.sap.cc.greeting;

import com.sap.hcp.cf.logging.common.LogContext;
import com.sap.hcp.cf.logging.common.request.HttpHeaders;
import com.sap.hcp.cf.logging.servlet.filter.RequestLoggingFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringBootGreetingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootGreetingApplication.class, args);
	}

	@Bean
	public WebClient webClient() {
		return WebClient.builder().filter((request, next) -> {
					ClientRequest filtered = ClientRequest.from(request)
							.header(HttpHeaders.CORRELATION_ID.getName(), LogContext.getCorrelationId())
							.build();
					return next.exchange(filtered);
				}
		).build();
	}

	@Bean
	public FilterRegistrationBean<RequestLoggingFilter> loggingFilter() {
		FilterRegistrationBean<RequestLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new RequestLoggingFilter());
		filterRegistrationBean.setName("request-logging");
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
		return filterRegistrationBean;
	}
}
