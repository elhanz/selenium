Feature: Booking
  Scenario: User should booking his flight in the site
    Given the user is on a main page
    When the user enters place from and place to
    And looking for flight
    Then the user should be booked his flight