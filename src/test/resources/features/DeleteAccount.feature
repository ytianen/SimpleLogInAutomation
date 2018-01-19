Feature: Delete Account

   Scenario Outline: Delete account profile
     Given I navigated to "http://www.fox.com"
     When I click on UserIcon
     And I can sign in using "<Email>" and "<Password>"
     When I click on UserIcon
     Then I can click on Edit Profile
     And delete my Profile

     Examples:
     |Email               |Password   |
     |amami05301@gmail.com|password222|