package com.drive.order.config;



import com.drive.common.jackson.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Configuration
public class JacksonConfiguration {

	public class CustomJackson2ObjectMapperBuilder extends Jackson2ObjectMapperBuilder {

		@Override
		public void configure(ObjectMapper objectMapper) {
			super.configure(objectMapper);
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			objectMapper.disable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
			objectMapper.disable(MapperFeature.AUTO_DETECT_GETTERS);
			objectMapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
			objectMapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
			objectMapper.disable(MapperFeature.AUTO_DETECT_CREATORS);
			objectMapper.enable(MapperFeature.AUTO_DETECT_FIELDS);
			objectMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
		}

	}

	@Bean
	@Primary
	Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {

		return new CustomJackson2ObjectMapperBuilder()
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.autoDetectGettersSetters(false)
			.autoDetectFields(true)
			.featuresToDisable(SerializationFeature.WRITE_NULL_MAP_VALUES, SerializationFeature.FAIL_ON_EMPTY_BEANS)
			.featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
			.featuresToEnable(MapperFeature.USE_STD_BEAN_NAMING)
			.featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
			.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
			.serializerByType(LocalDate.class, new LocalDateSerializer())
			.deserializerByType(LocalDate.class, new LocalDateDeserializer())
			.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());

	}

}
