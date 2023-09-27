Feature: Statistics

  @admin
  Scenario: Check statistics with an invalid period
  When the client calls "/statistics?startedAt=2023-09-25T00:00:00&endedAt=2023-09-20T23:59:59" with GET
  Then the response should have a status code of 400


  @admin
  Scenario: Check statistics with a valid period
  When the client calls "/statistics?startedAt=2023-09-20T00:00:00&endedAt=2023-09-25T23:59:59" with GET
  Then the response should have a status code of 200
  And the response body should be
  """
  {
    "numbersUsers": 0,
    "numbersApplyings": 0,
    "numbersVisits": 0,
    "numbersOffers": 0
  }
  """