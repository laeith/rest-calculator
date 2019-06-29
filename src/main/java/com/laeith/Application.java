package com.laeith;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  private static final Logger LOG = LogManager.getLogger(Application.class);

  public static void main(String[] args) {
    LOG.info("Calculator web service starts...");
    SpringApplication.run(Application.class, args);
  }

}
