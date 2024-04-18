package com.ensemble.entreprendre.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.client.IOffersClient;
import com.ensemble.entreprendre.domain.Offer;
import com.ensemble.entreprendre.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RequestMapping(path = "/api/offers")
@RestController
public class OfferController {

    @Autowired
    private IOffersClient offersClient;

    @GetMapping
    public Collection<Offer> getAll() throws ApiException, JsonMappingException, JsonProcessingException {
        return offersClient.getOffersFromEedkApi();
    }
}
