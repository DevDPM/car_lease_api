# car_lease_api

#### Technologies used:
- Quarkus / Microprofile
- Hibernate
- OpenApi
- Mapstruct
- Jwt
- Docker
- MySQL DB
- H2 in-memory DB

# See ./Car-lease-API.pptx for the architectural approach

#### Start the application with the following steps/commands:

#### Install all microservices
mvn clean install

#### Start MySQL DB via docker for customer service
docker run --name customerDB -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=customerDB -p 3306:3306 mysql:8.0

#### Start MySQL DB via docker for car service
docker run --name carDB -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=carDB -p 3307:3306 mysql:8.0

#### Run all applications locally <br><br>


#### Authentication & Endpoints API:

#### Example gateway api:
[GET] http://localhost:8003/login/{username} -> use jwt token for further calls with gateway api <br>
[GET/PUT/DELETE] http://localhost:8003/cars/1 <br>
[GET/POST] http://localhost:8003/cars <br>
[GET/PUT/DELETE] http://localhost:8003/customers/1 <br>
[GET/POST] http://localhost:8003/customers <br>
[GET] http://localhost:8003/cars/1/leaserate?mileage=45000&duration=60&interestrate=4.5 <br>

#### Authentication & Endpoints per microservice:
Auth Info for microservices below: <br>
For testing purpose go to endpoint without auth and get 401, terminal will output  <br>
JWT token, copy token and add in header as Authorization: Bearer {JWT-Token}, expiry time: 5 min <br>

##### Example customers endpoint:
[GET/PUT/DELETE] http://localhost:8000/customers/1 <br>
[GET/POST] http://localhost:8000/customers <br>

##### Example cars endpoint:
[GET/PUT/DELETE] http://localhost:8001/cars/1 <br>
[GET/POST] http://localhost:8001/cars <br>

##### Example leaserate endpoint:
[GET] http://localhost:8002/cars/1/leaserate?mileage=45000&duration=60&interestrate=4.5


#### DB Data only has 1 record for testing purpose and goes by ID: 1
