package nl.sog.gatewayapi.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sog.gatewayapi.di.carservice.client.CarsClient;
import nl.sog.gatewayapi.di.carservice.dto.CarDetails;
import nl.sog.gatewayapi.di.leaserateservice.client.LeaseRateClient;
import nl.sog.gatewayapi.di.leaserateservice.dto.LeaseRateDetails;
import nl.sog.gatewayapi.dto.CarDto;
import nl.sog.gatewayapi.dto.CarsDto;
import nl.sog.gatewayapi.dto.LeaseRateDto;
import nl.sog.gatewayapi.dto.mapper.CarMapper;
import nl.sog.gatewayapi.dto.mapper.LeaseRateMapper;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Provider
@RolesAllowed({"User"})
public class CarsResource implements CarsApi {

    private final CarsClient carsClient;
    private final CarMapper carMapper;
    private final LeaseRateClient leaseRateService;
    private final LeaseRateMapper leaseRateMapper;

    @Inject
    public CarsResource(CarsClient carsClient, CarMapper carMapper,
                        LeaseRateClient leaseRateService, LeaseRateMapper leaseRateMapper) {
        this.carsClient = carsClient;
        this.carMapper = carMapper;
        this.leaseRateService = leaseRateService;
        this.leaseRateMapper = leaseRateMapper;
    }

    @Override
    public Response createCar(CarDto carDto) {
        CarDetails carDetails = carMapper.toCarDetails(carDto);
        Long carId = carsClient.createCar(carDetails);
        return Response.created(URI.create("/cars/" + carId)).build();
    }

    @Override
    public Response deleteCar(Integer id) {
        int statusCode = carsClient.deleteCar(id);

        if (statusCode == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException("Unknown status code: " + statusCode);
        }
    }

    @Override
    public Response getAllCars() {
        List<CarDetails> carsDetails = carsClient.getAllCars();
        CarsDto carsDto = carMapper.toCarsDto(carsDetails);
        return Response.ok(carsDto).build();
    }

    @Override
    public Response getCar(Integer id) {
        CarDetails carDetails = carsClient.getCarDetails(id);
        CarDto carDto = carMapper.toCarDto(carDetails);
        return Response.ok(carDto).build();
    }

    @Override
    public Response getLeaserate(Integer id, Integer mileage, Integer duration, BigDecimal interestrate) {
        LeaseRateDetails leaserateDetails = leaseRateService.getLeaserateDetails(id, mileage, duration, interestrate);
        LeaseRateDto leaseRateDto = leaseRateMapper.toLeaseRateDto(leaserateDetails);
        return Response.ok(leaseRateDto).build();
    }

    @Override
    public Response updateCar(Integer id, CarDto carDto) {
        CarDetails carDetails = carMapper.toCarDetails(carDto);
        int statusCode = carsClient.updateCar(id, carDetails);

        return switch (statusCode) {
            case 204 -> Response.noContent().build();
            case 404 -> Response.status(Response.Status.NOT_FOUND).build();
            default -> throw new InternalServerErrorException("Unknown status code: " + statusCode);
        };
    }

}
