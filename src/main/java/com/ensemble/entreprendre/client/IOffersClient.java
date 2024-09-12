package com.ensemble.entreprendre.client;

import java.util.Collection;

import com.ensemble.entreprendre.domain.Offer;
import com.ensemble.entreprendre.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IOffersClient {
    public Collection<Offer> getOffersFromEedkApi() throws ApiException, JsonMappingException, JsonProcessingException;
}
