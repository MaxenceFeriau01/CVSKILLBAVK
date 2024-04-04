Feature: Users
  
  @user
  Scenario: Check changing "profile updating count" value
  When the client calls "/users/self" with GET
  Then the response should have a status code of 200
  And the response body should be ignoring "createdDate,lastModifiedDate"
  """
  {
    "id":1,
    "email":"modisROLE_USER@modis.com",
    "firstName":"modis",
    "name": "modis",
    "phone":"0161616161",
    "postalCode":7500,
    "dateOfBirth":"2023-01-01",
    "internshipStartDate":"2023-01-01",
    "internshipEndDate":"2023-01-01",
    "activated":true,
    "civility":"M.",
    "diploma":"BAC+5",
    "internshipPeriod":"6month",
    "searchSubject":"INTERNSHIP_SUBJECT",
    "internStatus": null,
    "files":[],
    "jobs":[],
    "roles":[
      {
        "id":1,
        "role":"ROLE_USER"
      }
    ],
    "appliedCompanies":[],
    "profileUpdateCount":0
  }
  """
  Given the client request body contains
  """
  {}
  """
  When the client calls "/users/self/no-profile-update" with POST
  Then the response should have a status code of 200
  Given the client request body as multipart-form-data contains
  """
  {
    "user": {
      "id":1,
      "email":"modisROLE_USER@modis.com",
      "firstName":"modis",
      "name": "modis",
      "phone":"0161616161",
      "postalCode":7500,
      "dateOfBirth":"2002-01-01",
      "internshipStartDate":"2023-01-01",
      "internshipEndDate":"2023-06-01",
      "activated":true,
      "civility":"M.",
      "diploma":"BAC+5",
      "internshipPeriod":"6month",
      "searchSubject":"INTERNSHIP_SUBJECT",
      "internStatus": {
        "id": 3,
        "name": "Etudiant"
      },
      "files":[],
      "jobs":[],
      "roles":[
        {
          "id":1,
          "role":"ROLE_USER"
        }
      ],
      "profileUpdateCount":0
    }
  }
  """
  When the client calls "/users/1" with PUT as multipart-form-data
  Then the response should have a status code of 200
  When the client calls "/users/self" with GET
  Then the response should have a status code of 200
  And the response body should be ignoring "createdDate,lastModifiedDate"
  """
  {
    "id":1,
    "email":"modisROLE_USER@modis.com",
    "firstName":"modis",
    "name": "modis",
    "phone":"0161616161",
    "postalCode":7500,
    "dateOfBirth":"2002-01-01",
    "internshipStartDate":"2023-01-01",
    "internshipEndDate":"2023-06-01",
    "activated":true,
    "civility":"M.",
    "diploma":"BAC+5",
    "internshipPeriod":"6month",
    "searchSubject":"INTERNSHIP_SUBJECT",
    "internStatus": {
      "id": 3,
      "name": "Etudiant"
    },
    "files":[],
    "jobs":[],
    "roles":[
      {
        "id":1,
        "role":"ROLE_USER"
      }
    ],
    "appliedCompanies":[],
    "profileUpdateCount":1
  }
  """
  Given the client request body as multipart-form-data contains
  """
  {
    "user": {
      "id":1,
      "email":"modisROLE_USER@modis.com",
      "firstName":"modis",
      "name": "modis",
      "phone":"0161616161",
      "postalCode":7500,
      "dateOfBirth":"2002-01-01",
      "internshipStartDate":"2023-01-01",
      "internshipEndDate":"2023-06-01",
      "activated":true,
      "civility":"M.",
      "diploma":"BAC+5",
      "internshipPeriod":"6month",
      "searchSubject":"INTERNSHIP_SUBJECT",
      "internStatus": {
        "id": 3,
        "name": "Etudiant"
      },
      "files":[],
      "jobs":[],
      "roles":[
        {
          "id":1,
          "role":"ROLE_USER"
        }
      ],
      "profileUpdateCount":1
    }
  }
  """
  When the client calls "/users/1" with PUT as multipart-form-data
  Then the response should have a status code of 200
  When the client calls "/users/self" with GET
  Then the response should have a status code of 200
  And the response body should be ignoring "createdDate,lastModifiedDate"
  """
  {
    "id":1,
    "email":"modisROLE_USER@modis.com",
    "firstName":"modis",
    "name": "modis",
    "phone":"0161616161",
    "postalCode":7500,
    "dateOfBirth":"2002-01-01",
    "internshipStartDate":"2023-01-01",
    "internshipEndDate":"2023-06-01",
    "activated":true,
    "civility":"M.",
    "diploma":"BAC+5",
    "internshipPeriod":"6month",
    "searchSubject":"INTERNSHIP_SUBJECT",
    "internStatus": {
      "id": 3,
      "name": "Etudiant"
    },
    "files":[],
    "jobs":[],
    "roles":[
      {
        "id":1,
        "role":"ROLE_USER"
      }
    ],
    "appliedCompanies":[],
    "profileUpdateCount":1
  }
  """
