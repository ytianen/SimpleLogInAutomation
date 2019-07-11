Feature: Create New Account
  @test
  Scenario Outline: Create new account
    Given I navigated to "http://www.fox.com"
    When I click on UserIcon
    Then I can click the Sign up button
    And fill in "FirstName" as "<First Name>"
    And fill in "LastName" as "<Last Name>"
    And fill in "Email" as "<Email>"
    And fill in "Password" as "<Password>"
    And select gender as "<Gender>"
    And fill in "Birthdate" as "<Birthdate>"
    Then click Create Profile
    And verify the text "Thanks for Signing Up" is displayed
    Then click the Done button
    And clean up by deleting the profile

    Examples:
      | First Name | Last Name  | Password    | Gender | Birthdate   |Email               |
      | Testfirst  | Testlast   | password222 | Male   | 01/01/1990  |amami05301@gmail.com|
