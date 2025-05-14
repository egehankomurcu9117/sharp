Feature: Kullanıcı API Testleri

  Background:
    * url 'https://api.example.com'
    * header Accept = 'application/json'

  Scenario: Kullanıcı listesini al
    Given path '/users'
    When method GET
    Then status 200
    And match response.data != []
    And match each response.data contains { id: '#number', email: '#string', first_name: '#string', last_name: '#string' }

  Scenario: Yeni kullanıcı oluştur
    Given path '/users'
    And request { name: 'Karate Test User', job: 'QA Engineer' }
    When method POST
    Then status 201
    And match response.name == 'Karate Test User'
    And match response.job == 'QA Engineer'
    And match response.id == '#string'
    * def userId = response.id

  Scenario: Kullanıcı güncelle
    * def userId = '123'
    Given path '/users/' + userId
    And request { name: 'Updated Karate User', job: 'Senior QA Engineer' }
    When method PUT
    Then status 200
    And match response.name == 'Updated Karate User'
    And match response.job == 'Senior QA Engineer'
    And match response.updatedAt == '#string'

  Scenario: Kullanıcı sil
    * def userId = '123'
    Given path '/users/' + userId
    When method DELETE
    Then status 204
