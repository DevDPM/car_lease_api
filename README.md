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

#### Start the application with the following steps/commands:

# Install all microservices
mvn clean install

# Start MySQL DB via docker for customer service
docker run --name customerDB -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=customerDB -p 3306:3306 mysql:8.0

# Start MySQL DB via docker for car service
docker run --name carDB -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=carDB -p 3307:3306 mysql:8.0

# Run all applications locally


#### Authentication & Endpoints Per microservice:

## For testing purpose go to endpoint without auth and get 401, terminal will output 
## JWT token, copy token and add in header as Authorization: Bearer {JWT-Token}

## Example customers endpoint:
http://localhost:8000/customers

## Example cars endpoint:
http://localhost:8001/cars/1

## Example leaserate endpoint:
http://localhost:8002/cars/1/leaserate?mileage=45000&duration=60&interestrate=4.5


#### DB Data only has 1 record for testing purpose and goes by ID: 1