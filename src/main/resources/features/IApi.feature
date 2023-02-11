@Home1
Feature:Testing api with rest assured
  Background: User get auth token
    Given I am an authorized user
  Scenario: This Scenario is created for employ data
    Given A list of books are available
    When I add a book to my reading list
    Then The book is added
    When I remove a book from my reading list
    Then The book is removed