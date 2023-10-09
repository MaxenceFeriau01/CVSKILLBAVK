package com.ensemble.entreprendre.features.core;



import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Component
public class HttpCallClient {

        private static final String BASE_URL = "http://localhost:8080/api";

        private ResponseEntity<JsonNode> response;

        private String jwtToken = "";

        WebClient webClient = WebClient.builder()
                        .baseUrl(BASE_URL)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .build();

        public void requestGet(final String url) {

                ResponseSpec responseSpec = this.webClient.get()
                                .uri(url)
                                .headers(h -> h.setBearerAuth(this.jwtToken))
                               
                                .retrieve()
                                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty());
                this.response = responseSpec.toEntity(JsonNode.class).block();
        }

        public void requestPost(final String url, final JsonNode body) {
                ResponseSpec responseSpec = this.webClient.post()
                                .uri(url)
                                .headers(h -> h.setBearerAuth(this.jwtToken))
                                .bodyValue(body)
                                .retrieve()
                                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty());
                this.response = responseSpec.toEntity(JsonNode.class).block();
        }

        public void requestPostAsMultipartFormData(final String url, final  MultipartBodyBuilder builder) {
                ResponseSpec responseSpec = this.webClient.post()
                                .uri(url)
                                .headers(h -> h.setBearerAuth(this.jwtToken))
                                .headers(h-> h.setContentType(MediaType.MULTIPART_FORM_DATA))
                                .body(BodyInserters.fromMultipartData(builder.build()))
                                .retrieve()
                                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty());
                this.response = responseSpec.toEntity(JsonNode.class).block();
        }

        public void requestPut(final String url, final JsonNode body) {
                ResponseSpec responseSpec = this.webClient.put()
                                .uri(url)
                                .headers(h -> h.setBearerAuth(this.jwtToken))
                                .bodyValue(body)
                                .retrieve()
                                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty());
                this.response = responseSpec.toEntity(JsonNode.class).block();
        }

        public void requestPutAsMultipartFormData(final String url, final  MultipartBodyBuilder builder) {
                ResponseSpec responseSpec = this.webClient.put()
                        .uri(url)
                        .headers(h -> h.setBearerAuth(this.jwtToken))
                        .headers(h-> h.setContentType(MediaType.MULTIPART_FORM_DATA))
                        .body(BodyInserters.fromMultipartData(builder.build()))
                        .retrieve()
                        .onStatus(HttpStatus::isError, clientResponse -> Mono.empty());
                this.response = responseSpec.toEntity(JsonNode.class).block();
        }

        public void requestPatch(final String url, final JsonNode body) {
                ResponseSpec responseSpec = this.webClient.patch()
                                .uri(url)
                                .headers(h -> h.setBearerAuth(this.jwtToken))
                                .bodyValue(body)
                                .retrieve()
                                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty());
                this.response = responseSpec.toEntity(JsonNode.class).block();
        }

        public void requestDelete(final String url) {
                ResponseSpec responseSpec = this.webClient.delete()
                                .uri(url)
                                .headers(h -> h.setBearerAuth(this.jwtToken))
                                .retrieve()
                                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty());
                this.response = responseSpec.toEntity(JsonNode.class).block();
        }

        public JsonNode getResponseBody() {
                return this.response.getBody();
        }

        public void setJwtToken(String jwtToken) {
                this.jwtToken = jwtToken;
        }

        public int getResponseStatusCode() {
                return this.response.getStatusCode().value();
        }

}
