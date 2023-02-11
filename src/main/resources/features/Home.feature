@Default
@UIAutomation1
@NQWER1
@Regression
Feature: TouchPoint Home Page


  @Home @SmokeTest @Home11
  Scenario Outline: Verify Home page
##    Given I Call Api To get data
#    Given I am navigate to "<Name>"
#    Then I should see sign-in in header section right
#    When I search for "Maui"
#    Then I should see "https://www.maui-rentals.com" results
    Given I am navigate to "https://www.google.co.nz/"
    When I search for "Brtiz"
    Then I should see "https://www.britz.com" results
    Examples:
    |Name|age|test|
  # |abc |23 |positive|
   |def |23 |positive|
  @NQWER
    @test
    @smoke
    @UAT

  Scenario: verify Amazon search
    Given I am navigate to "https://flipkart.com/"
    #When I search for "mobiles"




