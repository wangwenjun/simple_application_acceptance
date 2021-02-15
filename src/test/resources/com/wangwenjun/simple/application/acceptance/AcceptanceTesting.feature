@acceptance
Feature: Acceptance Function testing for Simple Application.

  Background: give the simple application url and port
    Given use the url "http://192.168.88.9" and port 18231

  Scenario: list all of employee
    When get "/employee"
    Then verify the list of result and http status code is 200

  Scenario Outline: get specify employee by id.
    When get "/employee/<ID>"
    Then the employee status code is 200 and employ name "<Name>"
    Examples:
      | ID | Name |
      | 1  | Alex |
      | 2  | Tina |
      | 3  | Jack |

  Scenario: delete the specify employee
  but should create the new one first.
    Given The name is "Alice",address is "UK" and remark is "Alice Remark"
    And Create new Employee Alice by uri "/employee"
    When Delete new Employee Alice by uri "/employee"
    Then The delete status code is 200

  Scenario: update the specify employee
  but should create the new one first.
    Given The name is "Alice",address is "UK" and remark is "Alice Remark"
    And Create new Employee Alice by uri "/employee"
    When Update Alice name to "Alice Wang" and uri "/employee"
    Then the update status code is 200 and updated name is "Alice Wang"
    And Delete new Employee for clean test data