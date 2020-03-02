package com.drive.mock.config;

import com.drive.common.inteceptor.CorrelationIdInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class HttpClientConfig {

	private MediaType contentType = new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8")
	);




	public List<HttpMessageConverter<?>> converters() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(getMappingJackson2HttpMessageConverter());
		return messageConverters;
	}

	@Bean
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {

		MappingJackson2HttpMessageConverter jsonConverter =
						new MappingJackson2HttpMessageConverter(getJacksonObjectMapper());
		jsonConverter.setSupportedMediaTypes(Arrays.asList(contentType));
		return jsonConverter;
	}

	@Bean
	@Primary
	public ObjectMapper getJacksonObjectMapper() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		builder.serializationInclusion(Include.NON_NULL);
		builder.modules(new JavaTimeModule());
		final ObjectMapper mapper = builder.build();
		mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, "@type");
		return mapper;
	}

	public RestTemplate defaultRestTemplateConfig() {

		RestTemplate restTemplate = new RestTemplate(converters());
		restTemplate.setInterceptors(Arrays.asList( new CorrelationIdInterceptor()));

		return restTemplate;

	}



}


