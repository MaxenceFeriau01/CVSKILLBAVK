
    Feature: Jobs statistics

    @admin
    Scenario: Check the jobs statistics

    When the client calls "/jobs/search" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "id": 1,
                "name": "Technicien",
                "companyCount": 0,
                "userCount": 0
            },
            {
                "id": 2,
                "name": "Juriste",
                "companyCount": 0,
                "userCount": 0
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 20,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "size": 20,
        "number": 0,
        "first": true,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "numberOfElements": 2,
        "empty": false
    }
    """

    When the client calls "/jobs/stats" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "name": "Juriste",
                "userCount": 0
            },
            {
                "name": "Technicien",
                "userCount": 0
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 20,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "size": 20,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 2,
        "empty": false
    }
    """

    @admin
    Scenario: Register a user and check the jobs statistics, create a company and check the jobs statistics again

    Given the client request body as multipart-form-data contains
    """
    {
        "user":{
            "cv": null,
            "coverLetter": null,
            "civility": "M",
            "name": "Nomtest",
            "firstName": "Prenomtest",
            "phone": "0777777777",
            "email": "test@test.com",
            "dateOfBirth": "2000-08-26",
            "postalCode": "00000",
            "password": "TESTT35T",
            "confirmPassword": "TESTT35T",
            "internshipStartDate": "2023-05-30",
            "internshipEndDate": "2023-09-30",
            "internStatus": {
                "id": 3,
                "name": "Etudiant"
            },
            "jobs": [
                {
                    "id": 1
                },
                {
                    "id": 2
                }
            ],
            "internshipPeriod": "2 à 6 mois",
            "diploma": "BAC + 5"  
        }
    }
    """
    When the client calls "/users/register" with POST as multipart-form-data
    Then the response should have a status code of 201

    When the client calls "/jobs/search" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "id": 1,
                "name": "Technicien",
                "companyCount": 0,
                "userCount": 1
            },
            {
                "id": 2,
                "name": "Juriste",
                "companyCount": 0,
                "userCount": 1
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 20,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "size": 20,
        "number": 0,
        "first": true,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "numberOfElements": 2,
        "empty": false
    }
    """

    When the client calls "/jobs/stats" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "name": "Juriste",
                "userCount": 1
            },
            {
                "name": "Technicien",
                "userCount": 1
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 20,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "size": 20,
        "number": 0,
        "first": true,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "numberOfElements": 2,
        "empty": false
    }
    """

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
            "paidAndLongTermInternship":false,
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
        "paidAndLongTermInternship":false
    }
    """

    When the client calls "/jobs/search" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "id": 1,
                "name": "Technicien",
                "companyCount": 1,
                "userCount": 1
            },
            {
                "id": 2,
                "name": "Juriste",
                "companyCount": 0,
                "userCount": 1
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 20,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "size": 20,
        "number": 0,
        "first": true,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "numberOfElements": 2,
        "empty": false
    }
    """

    When the client calls "/jobs/stats" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content": [
            {
                "name": "Juriste",
                "userCount": 1
            },
            {
                "name": "Technicien",
                "userCount": 1
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 20,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 2,
        "totalPages": 1,
        "size": 20,
        "number": 0,
        "first": true,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "numberOfElements": 2,
        "empty": false
    }
    """