package com.laeith.test.utils;

import com.laeith.infrastructure.configuration.SpringProfile;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;

/**
 * Base class for all integration tests.
 * Loads Spring context and provides all basic functionality
 */

@Tag("spring")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.test.yml")
@ActiveProfiles(SpringProfile.TEST)
public abstract class IntegrationTest {

  @LocalServerPort
  protected int port;
  protected String baseURL;
  protected TestRestTemplate restTemplate = new TestRestTemplate();

  @PostConstruct
  private void buildBaseURL() {
    baseURL = "http://localhost:" + port + "/calculator";
  }
}

