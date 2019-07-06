package com.laeith.test.utils;

import com.laeith.infrastructure.configuration.SpringProfile;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

/**
 * Base class for all integration tests.
 * Loads Spring context and provides all basic functionality
 */

@Tag("spring")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
@ActiveProfiles(SpringProfile.TEST)
public abstract class IntegrationTest {

  @LocalServerPort
  protected int port;
  protected String baseURL;
  protected final TestRestTemplate restTemplate = new TestRestTemplate();

  @PostConstruct
  private void buildBaseURL() {
    baseURL = "http://localhost:" + port + "/calculator";
  }

  protected <T> ResponseEntity<T> postWithFormParams(String endPoint,
                                                     MultiValueMap<String, String> params,
                                                     Class<T> expectedResponseType) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

    return restTemplate.exchange(
       baseURL + endPoint,
       HttpMethod.POST,
       requestEntity,
       expectedResponseType
    );
  }

}

