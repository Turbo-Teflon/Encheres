package fr.eni.encheres.controller.converter;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime>{

	@Override
	public LocalDateTime convert(String source) {
		
		return LocalDateTime.now().plusDays(Long.parseLong(source));
	}

}
