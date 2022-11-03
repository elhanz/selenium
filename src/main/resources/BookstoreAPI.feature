Feature: BookStore API
  Background: User generates token for Authorisation
    Given I am an authorized user
  Scenario: Authorized user is able to Add and Remove a book.
    Given A list of books are available
    When I add a book to my reading list
    Then The book is added
