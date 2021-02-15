package com.wangwenjun.simple.application.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class AcceptanceTestingStepDefs implements En {


  private ValidatableResponse response;

  private Map<String, Object> createPayload = new HashMap<>();
  private ObjectMapper mapper = new ObjectMapper();
  private int newId;

  public AcceptanceTestingStepDefs() {
    //back ground
    Given("use the url {string} and port {int}", (String url, Integer port) ->
    {

      RestAssured.baseURI = url;
      RestAssured.port = port;
    });

    //list all of employee
    When("get {string}", (String endpoint) ->
    {
      response = when().get(endpoint).then();
    });

    Then("verify the list of result and http status code is {int}", (Integer code) ->
    {
      response.statusCode(is(200));
    });

    //get the specify employee by id
    Then("the employee status code is {int} and employ name {string}", (Integer code, String name) ->
    {
      response.statusCode(is(200)).body("name", is(equalTo(name)));
    });

    Given("The name is {string},address is {string} and remark is {string}",
            (String name, String address, String remark) ->
            {
              createPayload.clear();
              createPayload.put("name", name);
              createPayload.put("address", address);
              createPayload.put("remark", remark);
              createPayload.put("createdAt", "2021-02-15");
              createPayload.put("updatedAt", "2021-02-15");
            });

    Given("Create new Employee Alice by uri {string}", (String uri) ->
    {
      newId = given().header("Content-Type", "application/json")
              .body(mapper.writeValueAsString(createPayload))
              .when().post(uri).then().statusCode(equalTo(200))
              .extract().body().jsonPath().getInt("id");
    });

    When("Delete new Employee Alice by uri {string}", (String uri) ->
    {
      response = when().delete(uri + "/" + newId).then();
    });

    Then("The delete status code is {int}", (Integer statusCode) ->
    {
      response.statusCode(is(200));
    });

    When("Update Alice name to {string} and uri {string}", (String newName, String uri) ->
    {
      createPayload.put("id", newId);
      createPayload.put("name", "Alice Wang");
      response = given().header("Content-Type", "application/json")
              .body(mapper.writeValueAsString(createPayload))
              .when().put(uri).then();
    });

    Then("the update status code is {int} and updated name is {string}", (Integer code, String name) ->
    {
      response.statusCode(is(200)).body("name", is(equalTo(name)));
    });

    And("Delete new Employee for clean test data", () ->
    {
      when().delete("employee/" + newId).then().statusCode(is(200));
    });

    //hook for clean resource.
    After("acceptance", RestAssured::reset);
  }
}
