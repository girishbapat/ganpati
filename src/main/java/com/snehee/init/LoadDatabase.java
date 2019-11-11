package com.snehee.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.snehee.ganpati.entity.Person;
import com.snehee.ganpati.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(PersonRepository repository) {
    return args -> {
    	 repository.save(new Person("Bilbo Baggins", "burglar"));
        repository.save(new Person("Frodo Baggins", "thief"));
      };
    }
}
    
