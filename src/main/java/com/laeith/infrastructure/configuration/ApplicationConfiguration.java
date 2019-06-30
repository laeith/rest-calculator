package com.laeith.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
//    Registers modules responsible for Java 8-specific classes serialization/deserialization.
    return new ObjectMapper()
       .registerModule(new ParameterNamesModule())
       .registerModule(new Jdk8Module())
       .registerModule(new JavaTimeModule());
  }
}
