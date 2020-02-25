package com.drive.orchestrator.config;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessEngineConfig implements ApplicationListener<ApplicationReadyEvent> {

	private final boolean enableJavaSerializationFormat;

	public ProcessEngineConfig(final @Value("${camunda.serialization-format.enabled:true}") boolean enableJavaSerializationFormat) {
		this.enableJavaSerializationFormat = enableJavaSerializationFormat;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		final SpringProcessEngineConfiguration engineConfiguration = applicationReadyEvent.getApplicationContext().getBean(
						SpringProcessEngineConfiguration.class);
		engineConfiguration.setJavaSerializationFormatEnabled(enableJavaSerializationFormat);
	}
}
