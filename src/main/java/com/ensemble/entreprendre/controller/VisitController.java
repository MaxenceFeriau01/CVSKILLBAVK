package com.ensemble.entreprendre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.IVisitService;

@RequestMapping(path = "/api/visits")
@RestController
public class VisitController {

    @Autowired
    private IVisitService visitService;

    @PostMapping
    public void addVisit() throws ApiException {
        visitService.addVisit();
    }

}
