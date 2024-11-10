package nl.sogeti.gatewayapi.resource;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.leaserateservice.dto.CarDto;
import nl.sogeti.leaserateservice.resource.CarsApi;

import java.math.BigDecimal;

@Provider
public class CarsResource implements CarsApi {


    @Override
    public Response createCar(CarDto carDto) {
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
