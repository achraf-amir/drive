package com.drive.orchestrator.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
public class DozerConfig {

	@Bean
	public DozerBeanMapper getDozerBeanMapper() {
		return new DozerBeanMapper(newArrayList("dozer/global.xml"));
	}
}
