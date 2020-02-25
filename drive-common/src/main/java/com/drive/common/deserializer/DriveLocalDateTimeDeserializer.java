package com.drive.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DriveLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

	public DriveLocalDateTimeDeserializer() {
		super(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	@Override
	public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		if (parser.hasToken(JsonToken.VALUE_STRING)) {
			String value = parser.getValueAsString();

			return LocalDateTime.parse(value);
		}

		return super.deserialize(parser, context);
	}
}
