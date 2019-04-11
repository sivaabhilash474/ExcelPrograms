Feature: Testing the login functionality
  
  Scenario: login functionality check with a valid user
  Given "Registered user" is authenticated
  Then I validate the following menu options are available:
  |Admin|
  |PIM|
  |Leave|
  |Time|
