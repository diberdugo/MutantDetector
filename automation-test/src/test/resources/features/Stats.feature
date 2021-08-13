Feature: This feature verifies the mutant stats


  Scenario: Validate stats
    Given I configured the mutants API
    When I want to get the mutant stats
    Then I will receipt HTTP code 200 as service response
    And I should see the stats info on the response
