package com.snehee.ganpati;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
/*@EntityScan(basePackages = "com.snehee.ganpati.entity") // Add this if entities are not in the same package
@EnableJpaRepositories(basePackages = "com.snehee.ganpati.repository")*/
public class GanpatiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(GanpatiApplication.class, args);
	}

}
