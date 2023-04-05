Feature: Get Instant Win

  Background: Reset the DB
    Given database is empty

  Scenario: Allocate Instant Win If your input matches winning transaction id
    Given  IW collection contains a document with storeId 123 and transaction id 3 and counter as 2
    When  User makes get call with storeId 123 and txnId 3
    Then User should be awarded IW
    And Counter should increment to 3

  Scenario: No IW is Allocated if input does not matches winning transaction id
    Given  IW collection contains a document with storeId 123 and transaction id 3 and counter as 1
    When  User makes get call with storeId 123 and txnId 3
    Then User should be not awarded IW
    And Counter should increment to 2
