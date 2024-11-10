package nl.sogeti.gatewayapi.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.gatewayapi.di.carservice.client.CarsClient;
import nl.sogeti.gatewayapi.di.leaserateservice.client.LeaserateClient;
import nl.sogeti.gatewayapi.dto.CarDto;

import java.math.BigDecimal;

@Provider
public class CarsResource implements CarsApi {

    private final CarsClient carsClient;
    private final LeaserateClient leaserateService;

    @Inject
    public CarsResource(CarsClient carsClient, LeaserateClient leaserateService) {
        this.carsClient = carsClient;
        this.leaserateService = leaserateService;
    }


    @Override
    public Response createCar(CarDto carDto) {
        // mapper
        carsClient.createCar(null);
        return null;
    }

    @Override
    public Response deleteCar(Integer id) {
        return null;
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
