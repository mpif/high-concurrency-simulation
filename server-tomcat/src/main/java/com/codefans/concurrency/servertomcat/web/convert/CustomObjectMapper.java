package com.codefans.concurrency.servertomcat.web.convert;

import com.codefans.concurrency.servercommon.data.Result;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Json输出格式转换
 */
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		CustomSerializerFactory factory = new CustomSerializerFactory();

		// 时间格式
		factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException,
					JsonProcessingException {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonGenerator.writeString(sdf.format(value));
			}
		});
		
		// Result枚举类格式
		factory.addGenericMapping(Result.class, new JsonSerializer<Result>() {
			@Override
			public void serialize(Result value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException,
					JsonProcessingException {
				jsonGenerator.writeNumber(value.getValue());
			}
		});
		
		// Boolean格式
        factory.addGenericMapping(Boolean.class, new JsonSerializer<Boolean>() {
            @Override
            public void serialize(Boolean value, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeNumber(value ? 1 : 0);
            }
        });
		this.setSerializerFactory(factory);
	}
}