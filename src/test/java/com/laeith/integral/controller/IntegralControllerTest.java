package com.laeith.integral.controller;

import com.laeith.infrastructure.web.GenericResponse;
import com.laeith.test.utils.IntegrationTest;
import com.laeith.test.utils.QuickTest;
import com.laeith.test.utils.SlowTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;


@QuickTest
class IntegralControllerTest extends IntegrationTest {

  @Test
  void correctIntegralControllerResponse() {
    var endpoint = "/integral";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("lowerBound", "2");
    params.add("upperBound", "5");
    params.add("processingUnits", "3");
    params.add("subintervals", "5000");

    ResponseEntity<GenericResponse> response = postWithFormParams(endpoint, params, GenericResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getErrors()).isNull();
    assertThat(response.getBody().getMessage()).isNull();
    assertThat(response.getBody().getData()).isNotNull();
  }

  @Test
  @SlowTest
  void shouldReturn500ForTooLongComputation() {
    var endpoint = "/integral";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("lowerBound", "2");
    params.add("upperBound", "5");
    params.add("processingUnits", "3");
    params.add("subintervals", String.valueOf(Long.MAX_VALUE - 1));

    ResponseEntity<GenericResponse> response = postWithFormParams(endpoint, params, GenericResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getErrors()).isNotNull().isNotEmpty();
  }

  @Test
  void shouldReturn400ForTooLowPrecision() {
    var endpoint = "/integral";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("lowerBound", "2");
    params.add("upperBound", String.valueOf(Double.MAX_VALUE));
    params.add("processingUnits", "3");
    params.add("subintervals", "500");

    ResponseEntity<GenericResponse> response = postWithFormParams(endpoint, params, GenericResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getErrors()).isNotNull().isNotEmpty();
  }

  @Test
  void shouldReturn400ForNonNumericInput() {
    var endpoint = "/integral";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("lowerBound", "2");
    params.add("upperBound", "some value that can't be parsed to number");
    params.add("processingUnits", "3");
    params.add("subintervals", "500");

    ResponseEntity<GenericResponse> response = postWithFormParams(endpoint, params, GenericResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getErrors()).isNotNull().isNotEmpty();
  }

  @Test
  void shouldReturn400ForOutOfRangeValues() {
    var endpoint = "/integral";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("lowerBound", "2");
    params.add("upperBound", "5");
    params.add("processingUnits", "6000000");
    params.add("subintervals", "500");

    ResponseEntity<GenericResponse> response = postWithFormParams(endpoint, params, GenericResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getErrors()).isNotNull().isNotEmpty();
  }

}