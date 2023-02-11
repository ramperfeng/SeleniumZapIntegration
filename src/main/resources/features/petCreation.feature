@NQWER12
Feature: Get Pet Details from PetStore
  Scenario Outline: <TCNO> Request valid pet details to the server <scenario>
    Given  User provided valid pet details "<payload>" for petCreation
    When  User request the petCreation endpoint with "<payload>"
    Then petCreation end point expected "<responsePayload>" as success response
    Examples:
      |TCNO                 | payload             |responsePayload     |scenario                                                 |
      |API_006_TS1_TC01     | petCreate1          | pet9900            |Creating the pet details based on user provided details  |
      |API_006_TS1_TC01     | petCreate2          | pet9900            |Creating the pet details based on user provided details  |
      |API_006_TS1_TC01     | petCreate3          | pet9900            |Creating the pet details based on user provided details  |
      |API_006_TS1_TC01     | petCreate4          | pet9900            |Creating the pet details based on user provided details  |
      |API_006_TS1_TC01     | petCreate1          | pet9900            |Creating the pet details based on user provided details  |
      |API_006_TS1_TC01     | petCreate1          | pet9900            |Creating the pet details based on user provided details  |
      |API_006_TS1_TC01     | petCreate1          | pet9900            |Creating the pet details based on user provided details  |
      |API_006_TS1_TC01     | petCreate1          | pet9900            |Creating the pet details based on user provided details  |

