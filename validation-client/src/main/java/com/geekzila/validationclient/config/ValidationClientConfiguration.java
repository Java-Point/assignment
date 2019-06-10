package com.geekzila.validationclient.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@EnableRetry
@Configuration
@PropertySource("classpath:application.yml")
public class ValidationClientConfiguration {

	@Value("${terminal.retry.timeout.seconds}")
	private int retryTimeout;

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.rootUri("http://terminal-server").build();
	}

	@Bean
	  public RetryTemplate retryTemplate() {
	    RetryTemplate retryTemplate = new RetryTemplate();

	    FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
	    fixedBackOffPolicy.setBackOffPeriod(5000L);
	    retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

	    TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
	    retryPolicy.setTimeout(TimeUnit.SECONDS.toMillis(retryTimeout));
	    retryTemplate.setRetryPolicy(retryPolicy);

	    return retryTemplate;
}
