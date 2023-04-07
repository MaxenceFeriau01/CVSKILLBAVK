
Feature: Posts

  @admin
  Scenario: Create a new post, activate it and search it

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

    When the client calls "/companies/search" with GET
    Then the response should have a status code of 200
    And the response body should be
    """
    {
        "content":[
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
                    "name":"BAMBECQUE",
                    "postalCode":"59046"
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
                    "name":"Droit"
                    }
                ],
                "searchedActivities":[
                    {
                    "id":1,
                    "name":"Droit"
                    }
                ],
                "searchedJobs":[
                    {
                    "id":1,
                    "name":"Technicien"
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
        ],
        "pageable":{
            "sort":{
                "empty":false,
                "sorted":true,
                "unsorted":false
            },
            "offset":0,
            "pageSize":10,
            "pageNumber":0,
            "paged":true,
            "unpaged":false
        },
        "last":true,
        "totalPages":1,
        "totalElements":1,
        "size":10,
        "number":0,
        "sort":{
            "empty":false,
            "sorted":true,
            "unsorted":false
        },
        "first":true,
        "numberOfElements":1,
        "empty":false
    }
    """

    @user
    Scenario: Create a new post, activate it and search it

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
    Then the response should have a status code of 403