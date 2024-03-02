# payment-search

This project represents the back-end api responsible for performing search queries in Elasticsearch.

## Technologies

* SpringBooot
* Java
* Elasticsearch Java Client

## How to run
Clone down this repository. You will need java 11 and maven installed on your machine.
The application can run in 2 modes -
* local
* cloud

### Run in local mode
Set up following properties in application.properties file

* es.connection.type=local
* es.local.url=connection url
* es.local.user=username
* es.local.password=password


Run the command
  
  ` mvn clean compile`
  `mvn spring-boot:run
  `
  

### Run in cloud mode
Set up following properties in application.properties file

* es.cloud.apikey=your api key
* es.cloud.url=connection url

Run the command
`mvn clean compile`
`
mvn spring-boot:run
`

### Example Requests

#### Perform Exact Search
`
curl --location 'http://localhost:8080/api/payments?limit=100&offset=64100' \
--header 'Content-Type: application/json' \
--data '{
"property":["Covered_Recipient_First_Name"],
"operator": "match",
"searchText": "ADA"
}'
`

#### Perform Prefix Match Search
`
curl --location 'http://localhost:8080/api/payments?limit=100&offset=64100' \
--header 'Content-Type: application/json' \
--data '{
"property":["Covered_Recipient_First_Name"],
"operator": "match_phrase_prefix",
"searchText": "ADA"
}'
`

#### Perform Multi Match Search
`
curl --location 'http://localhost:8080/api/payments?limit=10&offset=0' \
--data '{
"property":["Covered_Recipient_First_Name", "Covered_Recipient_Last_Name"],
"operator": "multi_match",
"searchText": "ADA"
}'
`