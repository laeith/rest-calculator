package com.laeith.calculator.controller;

import com.laeith.infrastructure.web.GenericResponse;
import com.laeith.infrastructure.web.RESTError;
import com.laeith.test.utils.IntegrationTest;
import com.laeith.test.utils.QuickTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;


@QuickTest
class CalculatorControllerTest extends IntegrationTest {

  @Test
  void correctEvaluationControllerResponse() {
    var endPoint = "/evaluate";

    HttpEntity<String> requestEntity = new HttpEntity<>("5 + 10");

    var response = restTemplate.exchange(
       baseURL + endPoint,
       HttpMethod.POST,
       requestEntity,
       GenericResponse.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getErrors()).isNull();
    assertThat(response.getBody().getMessage()).isNull();
    assertThat(response.getBody().getData()).isEqualTo("15");
  }

  @Test
  void checkProperResponseForIncorrectExpression() {
    var endPoint = "/evaluate";

    HttpEntity<String> requestEntity = new HttpEntity<>("5as + 10 ///12as");

    var response = restTemplate.exchange(
       baseURL + endPoint,
       HttpMethod.POST,
       requestEntity,
       RESTError.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isNotNull();
    assertThat(response.getBody().getDescription()).isNotNull();
  }
}