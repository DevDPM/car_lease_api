# car_lease_api

## MySQL DB via docker for customer service
docker run --name customerDB -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=customerDB -p 3306:3306 mysql:8.0

## MySQL DB via docker for car service
docker run --name carDB -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=carDB -p 3307:3306 mysql:8.0
