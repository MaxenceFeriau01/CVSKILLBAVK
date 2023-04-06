package com.ensemble.entreprendre.features.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.MultipartBodyBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinition {

    @Autowired
    HttpCallClient httpCallClient;

    private JsonNode body;

    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    // builder.part("company", body);

    @Autowired
    StepService stepService;

    ObjectMapper mapper = new ObjectMapper();

    @When("the client calls {string} with GET")
    public void the_client_calls_with_GET(String endpoint) {
        this.httpCallClient.requestGet(endpoint);
    }

    @When("the client calls {string} with POST")
    public void the_client_calls_with_POST(String endpoint) {
        this.httpCallClient.requestPost(endpoint, body);
    }

    @When("the client calls {string} with PUT")
    public void the_client_calls_with_PUT(String endpoint) {
        this.httpCallClient.requestPut(endpoint, body);
    }

    @When("the client calls {string} with DELETE")
    public void the_client_calls_with_DELETE(String endpoint) {
        this.httpCallClient.requestDelete(endpoint);
    }

    @When("the client calls {string} with PATCH")
    public void the_client_calls_with_Patch(String endpoint) {
        this.httpCallClient.requestPatch(endpoint, body);
    }

    @Given("the client request body contains")
    public void the_client_request_body_contains(String body) throws JsonMappingException, JsonProcessingException {
        this.body = mapper.readTree(body);
    }

    @Then("the response should have a status code of {int}")
    public void the_response_should_have_a_status_code_of(int statusCode) {
        assertEquals(statusCode, this.httpCallClient.getResponseStatusCode());
    }

    @And("the response body should be")
    public void the_response_body_should_be(String expectedResponse)
            throws JsonMappingException, JsonProcessingException {
        JsonNode expectedJson = this.mapper.readTree(expectedResponse);
        JsonNode responseFromApiCall = this.httpCallClient.getResponseBody();
        assertEquals(expectedJson, responseFromApiCall);
    }

    @And("the response body should be ignoring {string}")
    public void the_response_body_should_be(String fieldToIgnore, String expectedResponse)
            throws JsonMappingException, JsonProcessingException {
        JsonNode expectedJson = this.mapper.readTree(expectedResponse);
        JsonNode responseFromApiCall = this.httpCallClient.getResponseBody();

        stepService.removeKeyFromArrayNode(responseFromApiCall.findValue("content"), fieldToIgnore);
        this.stepService.removeKeyFromJsonNode(responseFromApiCall, fieldToIgnore);

        assertEquals(expectedJson, responseFromApiCall);
    }

    
    @Given("the client request body as multipart-form-data contains")
    public void the_client_request_body_contains_as_multipart_form_data(String body)
            throws JsonMappingException, JsonProcessingException {
        JsonNode jsonBody = mapper.readTree(body);
        this.builder = new MultipartBodyBuilder();
        jsonBody.fields().forEachRemaining(entry -> this.builder.part(entry.getKey(), entry.getValue()));
    }

    @When("the client calls {string} with POST as multipart-form-data")
    public void the_client_calls_with_POST_as_multipart_form_data(String endpoint) {
        this.httpCallClient.requestPostAsMultipartFormData(endpoint, builder);
    }

}