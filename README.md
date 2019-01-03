-----Description This application has been designed as apprenticeship task. It launches REST service to record organizations, rooms at wanted organization and reservations booking under wanted room and organization. 

It uses Spring Boot framework and is built with Gradle.

</br>Launching application creates endpoint for REST service at http://localhost:8080/organization/ At the start 2 organizations will be created to populate "database", all organizations are kept in app, there is no database at this point. 

Using POST request with body in JSON format will result in creating new organization if the organization name is not colliding (with existing ones). 

Using GET request will result in getting all recorded organizations in JSON format. 

Using GET request with an ID (http://localhost:8080/organization/1) will result in getting details of single organization with that ID  if the organization is present.

Using PUT request with body in JSON format will update already recorded organization, if the one  exists and if the organization name is not colliding. 

Using DELETE request with an ID  (http://localhost:8080/organization/1)  will delete single organization with that ID (if it was present).

Similar requests are respectfully made for room and reservation, with appropriate exceptions.

<h2></br>HOW TO BUILD APPLICATION:</h2>

Prerequirements:
1. Gradle installed (4.4+)
- application was built with gradle 4.10.3, I was trying to build it with gradle 3.5 and it failed with error message: "Spring Boot plugin requires Gradle 4.4 or later"
2. Git installed
3. Java 8 installed (I was using JDK 1.8.0_191)

</br>1. Open command line or git console 
</br>2. Go to folder on disk, let's say: C:\patronage
</br>3. Execute command: git clone https://github.com/AlexisReservator/ReservatorRepo.git 
</br>- a folder with project should have been created C:\patronage\ReservatorRepo
</br>4. Go to the created ReservatorRepo folder
</br>5. Execute command: gradle build

<h3>HOW TO RUN APPLICATION:</h3>
6. Start java application using the following command: java -jar build/libs/TheRoomReservator-0.0.1-SNAPSHOT.jar
7. Application starts at port 8080 in case of problems with port close already running process on this port




<h2></br>COMPLETE OF CURL COMMANDS FOR ORGANIZATION, ROOM, RESERVATION</h2>


CREATE ORGANIZATION

curl -X POST http://localhost:8080/organization/ -H 'Content-Type: application/json' -d '{"name": "OrganizacjaX"}'

- if organization(name) is already present it throws AlreadyExistsException: "Organization with name %s already exists.";

READ ORGANIZATION BY ID

curl -X GET  http://localhost:8080/organization/3

- if organization (id) is not present it throws NotFoundException: "Organization with this id does not exist.";

READ ALL ORGANIZATIONS

curl -X GET http://localhost:8080/organization

- reads all organizations;

UPDATE ORGANIZATION

curl -X PUT http://localhost:8080/organization/3 -H 'Content-Type: application/json' -d'{"name":"Stocznia Szczecin2"}'

- if organization (id) is not present it throws NotFoundException: "Organization with id %d does not exist.";
- if organization(name) is already present it throws AlreadyExistsException: "Organization with name %s already exists.";

DELETE ORGANIZATION

curl -X DELETE http://localhost:8080/organization/3

- if organization(with this id) is not present it throws NotFoundException: "Organization with id %d does not exist.";
- if organization was deleted it gives Message: "The organization was successfully deleted.";

CREATE ROOM

curl -X POST http://localhost:8080/organization/2/room -H 'Content-Type: application/json' -d '{
	"name": "PokojX",
	"floor": "7",
	"availability": "true",
	"sittingSpot": "40",
	"standingSpot": "70",
	"lyingSpot": "10",
	"hangingSpot": "30"
}'

- if organization (id) is not present it throws NotFoundException: ""The room can't be created in a non-existing organization."";
- if room(name) is already present it throws AlreadyExistsException: "Room with name %s already exists.";

READ ROOM BY ID

curl -X GET http://localhost:8080/organization/2/room/4

- if organization(id) is not present it throws NotFoundException: "The room under non-existing organization doesn't exist.";
- if room (id) is not present it throws NotFoundException: "Room with this id does not exist.";
- if the room under given organization (id) is not present it throws OtherException: "The room under this organization doesn't exist.;

READ ALL ROOMS

curl -X GET http://localhost:8080/organization/2/room

- if organization(id) is not present it throws: "There are no rooms under non-existing organization.";
- if room under given organization (id) is not present it throws - OtherException: "There are no rooms under this organization.";

UPDATE ROOM

curl -X PUT http://localhost:8080/organization/2/room/2 -H 'Content-Type: application/json' -d '{ 
	"name": "PokojY",
	"floor": "5",
	"availability": "true",
	"sittingSpot": "20",
	"standingSpot": "50",
	"lyingSpot": "20",
	"hangingSpot": "10"
}'

- if room (id) is not present it throws NotFoundException: "Room with id %d does not exist. So it can't be updated.";
- if room(name) is already present it throws AlreadyExistsException: "Room with name %s already exists.";
- if organization (id) is not present it throws NotFoundException: "The room under non-existing organization doesn't exist. So it can't be updated.";
- if room under given organization (id) is not present it throws - OtherException: "There are no rooms under this organization. So it can't be updated.";

DELETE ROOM

curl -X DELETE http://localhost:8080/organization/2/room/4

- if room (id) is not present it throws NotFoundException: "Room with id %d does not exist.";
- if organization (id) is not present it throws NotFoundException: "The room under non-existing organization can't be deleted.";
- if room under given organization (id) is not present it throws OtherException: "The room under this organization doesn't exist. So it can't be deleted.";
- if room was deleted it gives Message: "The room was successfully deleted."; 

CREATE RESERVATION

curl -X POST http://localhost:8080/organization/2/room/2/reservation -H 'Content-Type: application/json' -d '{
	"name": "Organizacja2",
	"userId": "ZosiaK",
	"startDate": "2019-01-01 16:00 UTC",
	"endDate": "2019-01-01 19:00 UTC"
}'

- if dates for reservations in the same room are colliding it throws AlreadyExistsException: "The room with chosen date is already booked. Choose different date. Colliding reservations: %s"
- if startDate is later than endDate it throws OtherException: "Incorrect dates format. End date can't be earlier than start date.";
- if room (id) is not present it throws NotFoundException: "The reservation can't be created in a non-existing room.";
- if organization (id) is not present it throws NotFoundException: "The reservation can't be created in a non-existing organization.";

READ RESERVATION BY ID

curl -X GET http://localhost:8080/organization/1/room/1/reservation/1

- if there is no such room, no such organization and no such reservation it throws OtherException: "There is no such reservation, no such room and no such organization.";
- if there is no such room and no such organization it throws OtherException: "There is no reservation under non-existing room and non-existing organization.";
- if there is no such organization it throws NotFoundExceptionException: "There is no reservation in a non-existing organization.";
- if there is no such room it throws NotFoundException: "There is no reservation in a non-existing room.";
- if there is no such reservation: "Reservation with id %d does not exist. So it can't be updated.";

READ ALL RESERVATIONS

curl -X GET http://localhost:8080/organization/1/room/1/reservation

- if there is no such room and no such organization it throws OtherException: "There is no reservation under non-existing room and non-existing organization.";
- if there is no such organization it throws NotFoundExceptionException: "There is no reservation in a non-existing organization.";
- if there is no such room it throws NotFoundException: "There is no reservation in a non-existing room.";
- if there is any reservations it throws NotFoundException: "There is no reservations under this room.";

UPDATE RESERVATION

curl -X PUT http://localhost:8080/organization/1/room/2/reservation/2 -H 'Content-Type: application/json' -d '{
	"userId": "Ale",
	"startDate": "2019-01-11 16:00 UTC",
	"endDate": "2019-01-11 19:00 UTC"
}'

- if there is no such room, no such organization and no such reservation it throws OtherException: "There is no such reservation, no such room and no such organization. So it can't be updated.";
- if there is no such room and no such organization it throws OtherException: "There is no reservation under non-existing room and non-existing organization. So it can't be updated.";
- if there is no such organization it throws NotFoundExceptionException: "There is no reservation in a non-existing organization. So it can't be updated.";
- if there is no such room it throws NotFoundException: "There is no reservation in a non-existing room. So it can't be updated.";
- if on chosen room reservation is already made it throws AlreadyExistsException: "Room with chosen date is already booked; choose different date; colliding reservations: %s.";
- if there is no such reservation it throws NotFoundException: "Reservation with id %d does not exist.";

DELETE RESERVATION

curl -X DELETE http://localhost:8080/organization/1/room/1/reservation/1

- if there is no such room, no such organization and no such reservation it throws OtherException: "The non-existing reservation at non-existing room and non-existing organization can't be deleted.";
- if there is no such room and no such organization it throws OtherException: "The reservation can't be deleted under non-existing room and non-existing organization.";
- if there is no such organization it throws NotFoundExceptionException: "The reservation can't be deleted under non-existing organization.";
- if there is no such room it throws NotFoundException: "The reservation can't be deleted under non-existing room.";
- if there is no such reservation: "Reservation with id %d does not exist.";
- if reservation was deleted it gives Message: "The reservation was successfully deleted."; 
