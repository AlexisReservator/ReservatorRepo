<h2>Creation of reservation</h2>

<h3>Create organization</h3>
curl -X POST http://localhost:8080/organizations/ -H 'Content-Type: application/json' -d '{"name": "Organizacja1"}'

<h3>Create room</h3>
curl -X POST http://localhost:8080/organizations/1/rooms -H 'Content-Type: application/json' -d '{
"name": "Pokoj1", "floor": "7", "availability": "true", "sittingSpot": "40", "standingSpot": "70", "lyingSpot": "0", "hangingSpot": "30"}'

<h3>Create reservation</h3>
curl -X POST http://localhost:8080/organizations/1/rooms/1/reservations -H 'Content-Type: application/json' -d '{
"userId": "jakis",
"startDate": "2019-02-05 13:00",
"endDate": "2019-02-05 15:00"
}'

<h2>Obtaining reservation</h2>
curl -X GET http://localhost:8080/organizations/1/rooms/1/reservations/1

<h2>Building and running application</h2>
<h3>Prerequirements</h3>
<ul>
<li>Gradle installed (4.4+). Application was built with gradle 4.10.3, while trying to build it with gradle 3.5 - it failed with error message: "Spring Boot plugin requires Gradle 4.4 or later".</li>
<li>Git installed.</li>
<li>Java 11 installed.</li>
</ul>
<h3>Building and running</h3>
<ol>
  <li>Go to the ReservatorRepo folder after cloning repository</li>
  <li>Execute command: gradle build</li>
  <li>Start java application using the following command: java -jar build/libs/TheRoomReservator-0.0.1-SNAPSHOT.jar </li>
  <li>Application starts at port 8080 in case of problems with port close already running process on this port</li>
</ol>
