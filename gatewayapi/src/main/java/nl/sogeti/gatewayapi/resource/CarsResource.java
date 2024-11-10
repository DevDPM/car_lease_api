package nl.sogeti.gatewayapi.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.NoContentException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.gatewayapi.di.carservice.client.CarsClient;
import nl.sogeti.gatewayapi.di.carservice.dto.CarDetails;
import nl.sogeti.gatewayapi.di.leaserateservice.client.LeaserateClient;
import nl.sogeti.gatewayapi.dto.CarDto;
import nl.sogeti.gatewayapi.dto.mapper.CarMapper;

import java.math.BigDecimal;
import java.net.URI;

@Provider
public class CarsResource implements CarsApi {

    private final CarsClient carsClient;
    private final CarMapper carMapper;
    private final LeaserateClient leaserateService;

    @Inject
    public CarsResource(CarsClient carsClient, CarMapper carMapper,
                        LeaserateClient leaserateService) {
        this.carsClient = carsClient;
        this.carMapper = carMapper;
        this.leaserateService = leaserateService;
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
        return null;
    }

    @Override
    public Response getCar(Integer id) {
        return null;
    }

    @Override
    public Response getLeaserate(Integer id, Integer mileage, Integer duration, BigDecimal interestrate) {
        return null;
    }

    @Override
    public Response updateCar(Integer id, CarDto carDto) {
        return null;
    }

}
