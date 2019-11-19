package com.snehee.ganpati;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

@Configuration
class DateTimeConfig {

	@Bean
	public FormattingConversionService conversionService() {
		final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

		final DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
		registrar.setDateFormatter(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
		registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a"));
		registrar.registerFormatters(conversionService);

		// other desired formatters

		return conversionService;
	}
}
