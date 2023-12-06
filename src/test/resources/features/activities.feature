
Feature: Activities statistics

    @admin
    Scenario: Check the activities statistics

    When the client calls "/activities/search" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "id": 1,
                "name": "Droit",
                "companyCount": 0,
                "companySearchCount": 0
            },
            {
                "id": 2,
                "name": "Mécanique",
                "companyCount": 0,
                "companySearchCount": 0
            }
        ],
        "pageable": {
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "pageSize": 20,
            "pageNumber": 0,
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "numberOfElements": 2,
        "first": true,
        "number": 0,
        "size": 20,
        "empty": false
    }
    """

    @admin
    Scenario: Create a company, check the activities statistics, create another company and then check the activities statistics again

    Given the client request body as multipart-form-data contains
    """
    {
        "company":{
            "type":"Entreprise",
            "logo":null,
            "name":"Akkodis",
            "siret":"42295086545450",
            "activities":[
                {
                    "id":1
                }
            ],
            "websiteUrl":"https://www.akkodis.com/",
            "description":"",
            "contactFirstName":"Adrien",
            "contactLastName":"Holvoet",
            "contactMail":"adrien.holvoet@outlook.com",
            "contactNum":"+33616382918",
            "fixContactNum":"",
            "address":"Marc en bareuil",
            "city":{
                "id":1
            },
            "minorAccepted":false,
            "searchedInternsType":[
                {
                    "periods":[
                    "Bac professionnel/Technique jusqu’à 8 semaines de stages"
                    ],
                    "internStatus":{
                    "id":2,
                    "name":"Lycéen"
                    }
                },
                {
                    "periods":[
                    "15 jours à 2 mois"
                    ],
                    "internStatus":{
                    "id":3,
                    "name":"Etudiant"
                    }
                }
            ],
            "searchedActivities":[
                {
                    "id":1
                }
            ],
            "searchedJobs":[
                {
                    "id":1
                }
            ],
            "isPaidAndLongTermInternship":false,
            "desiredInternsNumber":"1 à 5"
        }
    }
    """
    When the client calls "/companies" with POST as multipart-form-data
    Then the response should have a status code of 201
    And the response body should be
    """
    {
        "id":1,
        "name":"Akkodis",
        "contactFirstName":"Adrien",
        "contactLastName":"Holvoet",
        "contactMail":"adrien.holvoet@outlook.com",
        "contactNum":"+33616382918",
        "fixContactNum":"",
        "websiteUrl":"https://www.akkodis.com/",
        "siret":"42295086545450",
        "description":"",
        "logo":null,
        "address":"Marc en bareuil",
        "type":"Entreprise",
        "city":{
            "id":1,
            "name":null,
            "postalCode":null
        },
        "region":null,
        "department":null,
        "epci":null,
        "desiredInternsNumber":"1 à 5",
        "activated":true,
        "minorAccepted":false,
        "activities":[
            {
                "id":1,
                "name":null
            }
        ],
        "searchedActivities":[
            {
                "id":1,
                "name":null
            }
        ],
        "searchedJobs":[
            {
                "id":1,
                "name":null
            }
        ],
        "searchedInternsType":[
            {
                "id":1,
                "internStatus":{
                    "id":2,
                    "name":"Lycéen"
                },
                "periods":[
                    "Bac professionnel/Technique jusqu’à 8 semaines de stages"
                ]
            },
            {
                "id":2,
                "internStatus":{
                    "id":3,
                    "name":"Etudiant"
                },
                "periods":[
                    "15 jours à 2 mois"
                ]
            }
        ],
        "isPaidAndLongTermInternship":false
    }
    """

    When the client calls "/activities/search" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "id": 1,
                "name": "Droit",
                "companyCount": 1,
                "companySearchCount": 1
            },
            {
                "id": 2,
                "name": "Mécanique",
                "companyCount": 0,
                "companySearchCount": 0
            }
        ],
        "pageable": {
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "pageSize": 20,
            "pageNumber": 0,
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "numberOfElements": 2,
        "first": true,
        "number": 0,
        "size": 20,
        "empty": false
    }
    """

    Given the client request body as multipart-form-data contains
    """
    {
        "company":{
            "type":"Entreprise",
            "logo":null,
            "name":"Test company",
            "siret":"00000000000000",
            "activities":[
                {
                    "id":1
                }
            ],
            "websiteUrl":"https://www.test.com/",
            "description":"",
            "contactFirstName":"Adrien",
            "contactLastName":"Holvoet",
            "contactMail":"adrien.holvoet@outlook.com",
            "contactNum":"+33616382918",
            "fixContactNum":"",
            "address":"Marc en bareuil",
            "city":{
                "id":1
            },
            "minorAccepted":false,
            "searchedInternsType":[
                {
                    "periods":[
                    "Bac professionnel/Technique jusqu’à 8 semaines de stages"
                    ],
                    "internStatus":{
                    "id":2,
                    "name":"Lycéen"
                    }
                },
                {
                    "periods":[
                    "15 jours à 2 mois"
                    ],
                    "internStatus":{
                    "id":3,
                    "name":"Etudiant"
                    }
                }
            ],
            "searchedActivities":[
                {
                    "id":1
                },
                {
                    "id":2
                }
            ],
            "searchedJobs":[
                {
                    "id":1
                }
            ],
            "isPaidAndLongTermInternship":false,
            "desiredInternsNumber":"1 à 5"
        }
    }
    """
    When the client calls "/companies" with POST as multipart-form-data
    Then the response should have a status code of 201
    And the response body should be
    """
    {
        "id":2,
        "name":"Test company",
        "contactFirstName":"Adrien",
        "contactLastName":"Holvoet",
        "contactMail":"adrien.holvoet@outlook.com",
        "contactNum":"+33616382918",
        "fixContactNum":"",
        "websiteUrl":"https://www.test.com/",
        "siret":"00000000000000",
        "description":"",
        "logo":null,
        "address":"Marc en bareuil",
        "type":"Entreprise",
        "city":{
            "id":1,
            "name":null,
            "postalCode":null
        },
        "region":null,
        "department":null,
        "epci":null,
        "desiredInternsNumber":"1 à 5",
        "activated":true,
        "minorAccepted":false,
        "activities":[
            {
                "id":1,
                "name":null
            }
        ],
        "searchedActivities":[
            {
                "id":1,
                "name":null
            },
            {
                "id":2,
                "name":null
            }
        ],
        "searchedJobs":[
            {
                "id":1,
                "name":null
            }
        ],
        "searchedInternsType":[
            {
                "id":3,
                "internStatus":{
                    "id":2,
                    "name":"Lycéen"
                },
                "periods":[
                    "Bac professionnel/Technique jusqu’à 8 semaines de stages"
                ]
            },
            {
                "id":4,
                "internStatus":{
                    "id":3,
                    "name":"Etudiant"
                },
                "periods":[
                    "15 jours à 2 mois"
                ]
            }
        ],
        "isPaidAndLongTermInternship":false
    }
    """

    When the client calls "/activities/search" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "id": 1,
                "name": "Droit",
                "companyCount": 2,
                "companySearchCount": 2
            },
            {
                "id": 2,
                "name": "Mécanique",
                "companyCount": 0,
                "companySearchCount": 1
            }
        ],
        "pageable": {
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "pageSize": 20,
            "pageNumber": 0,
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "numberOfElements": 2,
        "first": true,
        "number": 0,
        "size": 20,
        "empty": false
    }
    """
