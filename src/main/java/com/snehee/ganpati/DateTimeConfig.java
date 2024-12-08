package com.snehee.ganpati;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.format.DateTimeFormatter;

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

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
}
