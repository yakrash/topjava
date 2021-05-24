CURL requests for MealRestController testing

Get all:
curl -v "http://localhost:8080/topjava/rest/meals"

Filter meals:
curl -v "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=00:00:00&endDate=2020-01-30&endTime=23:00:00"

Get meal by id:
curl -v "http://localhost:8080/topjava/rest/meals/100002"

Create meal:
curl -v "http://localhost:8080/topjava/rest/meals" -H "Content-Type: application/json" -d "{\"dateTime\":\"2021-05-24T12:00:00\",\"description\":\"Perekus\",\"calories\":1000}" 

Update meal:
curl -X PUT -v "http://localhost:8080/topjava/rest/meals/100002" -H "Content-Type: application/json" -d "{\"id\":100002,\"dateTime\":\"2021-05-24T11:30:00\",\"description\":\"2xPerekus\",\"calories\":2000}"

Delete meal:
curl -X DELETE -v "http://localhost:8080/topjava/rest/meals/100002"