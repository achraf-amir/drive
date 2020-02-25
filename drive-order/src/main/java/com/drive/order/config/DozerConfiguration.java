package com.drive.order.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
public class DozerConfiguration {

	@Bean
	public DozerBeanMapper getDozerBeanMapper() {
		return new DozerBeanMapper(newArrayList("dozer/global.xml"));
	}
}
