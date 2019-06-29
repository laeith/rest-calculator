package com.laeith.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("application")
public class ApplicationProperties {

  private final Database database = new Database();

  @Setter
  @Getter
  public static class Database {
    private String url;
    private String username;
    private String password;
    private String ipAddress;
  }

}

