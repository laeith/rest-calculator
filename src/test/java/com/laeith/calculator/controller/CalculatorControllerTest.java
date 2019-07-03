package com.laeith.calculator.controller;

import com.laeith.infrastructure.web.GenericResponse;
import com.laeith.test.utils.IntegrationTest;
import com.laeith.test.utils.QuickTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;


@QuickTest
class CalculatorControllerTest extends IntegrationTest {

  @Test
  void correctEvaluationControllerResponse() {
    var endpoint = "/evaluate";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("expression", "5 + 10");

    ResponseEntity<GenericResponse> response = postWithFormParams(endpoint, params, GenericResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getErrors()).isNull();
    assertThat(response.getBody().getMessage()).isNull();
    assertThat(response.getBody().getData()).isEqualTo("15");
  }

  @Test
  void shouldReturn400ForIncorrectExpression() {
    var endpoint = "/evaluate";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("expression", "5as + 10 ///12as");

    var response = postWithFormParams(endpoint, params, GenericResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isNotNull();
    assertThat(response.getBody().getErrors()).isNotNull().isNotEmpty();
  }

  @Test
  void shouldReturn400ForArithmeticErrors() {
    var endpoint = "/evaluate";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("expression", "5/0");

    var response = postWithFormParams(endpoint, params, GenericResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isNotNull();
    assertThat(response.getBody().getErrors()).isNotNull().isNotEmpty();
  }
}