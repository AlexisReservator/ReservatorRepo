<h2>Creation of reservation</h2>

<h3>Create organization</h3>
curl -X POST http://localhost:8080/organizations/ -H 'Content-Type: application/json' -d '{"name": "Organizacja1"}'

CREATE ROOM:
curl -X POST http://localhost:8080/organizations/1/rooms -H 'Content-Type: application/json' -d '{
"name": "Pokoj1", "floor": "7", "availability": "true", "sittingSpot": "40", "standingSpot": "70", "lyingSpot": "0", "hangingSpot": "30"}'

CREATE RESERVATION:
curl -X POST http://localhost:8080/organizations/1/rooms/1/reservations -H 'Content-Type: application/json' -d '{
"userId": "jakis",
"startDate": "2019-02-05 13:00",
"endDate": "2019-02-05 15:00"
}'

<h2>Obtaining reservation</h2>
curl -X GET http://localhost:8080/organizations/1/rooms/1/reservations/1



PREREQUIREMENTS:
- Gradle installed (4.4+); application was built with gradle 4.10.3, while trying to build it with gradle 3.5 - it failed with error message: "Spring Boot plugin requires Gradle 4.4 or later";
- Git installed;
- Java 11 installed;

HOW TO BUILD APPLICATION:
1. Go to the ReservatorRepo folder after cloning repository
2. Execute command: gradle build
HOW TO RUN APPLICATION:
3. Start java application using the following command: java -jar build/libs/TheRoomReservator-0.0.1-SNAPSHOT.jar 
4. Application starts at port 8080 in case of problems with port close already running process on this port
