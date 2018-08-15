Feature: Perform integrated tests on the Avengers registration API

Background:
* url 'https://hrkxj65omb.execute-api.us-east-2.amazonaws.com/dev'

Scenario: Get Avenger by Id

Given path 'avengers', 'sdsa-sasa-asas-sasa'
When method get
Then status 200
And match response == {id: '#string', name: 'Iron Man', secretIdentity: 'Tony Stark'}

Scenario: Avenger Not Found

Given path 'avengers', 'invalid'
When method get
Then status 404

Scenario: Registry a new Avenger

Given path 'avengers'
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method post
Then status 201
And match response == {id: '#string', name: 'Captain America', secretIdentity: 'Steve Rogers'}

Scenario: Delete a Avenger by Id

Given path 'avengers', 'sdsa-sasa-asas-sasa'
When method delete
Then status 204

Scenario: Delete a Avenger Not Found

Given path 'avengers', 'invalid'
When method delete
Then status 404

Scenario: Update a Avenger by Id

Given path 'avengers', 'sdsa-sasa-asas-sasa'
And request {name: 'Hulk', secretIdentity: 'Bruce'}
When method put
Then status 200
And match response == {id: '#string', name: 'Hulk', secretIdentity: 'Bruce'}

Scenario: Registry Avenger with Invalid Payload

Given path 'avengers'
And request {secretIdentity: 'Steve Rogers'}
When method post
Then status 400

Scenario: Update Avenger with Invalid Payload

Given path 'avengers', 'sdsa-sasa-asas-sasa'
And request {secretIdentity: 'Steve Rogers'}
When method put
Then status 400


