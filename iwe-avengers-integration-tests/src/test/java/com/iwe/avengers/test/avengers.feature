Feature: Perform integrated tests on the Avengers registration API

Background:
* url 'https://hrkxj65omb.execute-api.us-east-2.amazonaws.com/dev'

* def getToken =
"""
function() {
 var TokenGenerator = Java.type('com.iwe.avengers.test.authorization.TokenGenerator');
 var sg = new TokenGenerator();
 return sg.getToken();
}
"""
* def token = call getToken

Scenario: Should return Unauthorized access
Given path 'avengers', 'anyid'
When method get
Then status 401


Scenario: Avenger Not Found
Given path 'avengers', 'invalid'
And header Authorization = 'Bearer ' + token
When method get
Then status 404

Scenario: Registry a new Avenger
Given path 'avengers'
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method post
Then status 201
And match response == {id: '#string', name: 'Captain America', secretIdentity: 'Steve Rogers'}

* def savedAvenger = response

#Get Avenger by id
Given path 'avengers', savedAvenger.id
And header Authorization = 'Bearer ' + token
When method get
Then status 200
And match $ == savedAvenger

Scenario: Delete a Avenger by Id

#Create a new Avenger
Given path 'avengers'
And header Authorization = 'Bearer ' + token
And request {name: 'Hulk', secretIdentity: 'Bruce Banner'}
When method post
Then status 201

* def avengerToDelete = response

#Delete the Avenger
Given path 'avengers', avengerToDelete.id
And header Authorization = 'Bearer ' + token
When method delete
Then status 204

#Search deleted Avenger
Given path 'avengers', avengerToDelete.id
And header Authorization = 'Bearer ' + token
When method get
Then status 404

Scenario: Attempt to delete a non-existent Avenger
Given path 'avengers', 'sss-ddd-fff-eee'
And header Authorization = 'Bearer ' + token
When method delete
Then status 404

Scenario: Update a Avenger by Id

#Create a new Avenger
Given path 'avengers'
And header Authorization = 'Bearer ' + token
And request {name: 'Captain', secretIdentity: 'Steve'}
When method post
Then status 201

* def avengerToUpdate = response

Given path 'avengers', avengerToUpdate.id
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method put
Then status 200
And match $.id == avengerToUpdate.id
And match $.name == 'Captain America'
And match $.secretIdentity == 'Steve Rogers'

Scenario: Attempt to upgrade a non-existent Avenger

Given path 'avengers', 'nao-existo'
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method put
Then status 404

Scenario: Registry Avenger with Invalid Payload

Given path 'avengers'
And header Authorization = 'Bearer ' + token
And request {secretIdentity: 'Steve Rogers'}
When method post
Then status 400

Scenario: Update Avenger with Invalid Payload

Given path 'avengers', 'sdsa-sasa-asas-sasa'
And header Authorization = 'Bearer ' + token
And request {secretIdentity: 'Steve Rogers'}
When method put
Then status 400

