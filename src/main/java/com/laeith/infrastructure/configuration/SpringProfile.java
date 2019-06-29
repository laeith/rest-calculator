package com.laeith.infrastructure.configuration;

/**
 * This can't be an enum because Spring's @Profile annotation accepts only primitives / Strings
 */
public class SpringProfile {

  /**
   * Used for any environment to run in production mode
   */
  public static final String PRODUCTION = "production";

  /**
   * Both DEV and TEST are used for local development activities and for more sophisticated testing
   * (integration, functional).
   */
  public static final String DEV = "development";
  public static final String TEST = "test";

  private SpringProfile() {
  }

}
