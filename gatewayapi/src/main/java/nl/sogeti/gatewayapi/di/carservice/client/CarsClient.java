package nl.sogeti.gatewayapi.di.carservice.client;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import nl.sogeti.gatewayapi.di.carservice.ApiException;
import nl.sogeti.gatewayapi.di.carservice.CarsApi;
import nl.sogeti.gatewayapi.di.carservice.dto.CarDetails;
import nl.sogeti.gatewayapi.di.common.provider.JwtAuthTokenProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

@Dependent
public class CarsClient {

    private final URI carsURI = URI.create("http://localhost:8001/");

    public URI createCar(CarDetails carDetails) {
        try {
            Response response = getCarsApi().create(carDetails);
            return response.getLocation();
        } catch (ApiException | ProcessingException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public CarDetails getCarDetails(Integer carId) {
        try {
            return getCarsApi().get(carId);
        } catch (ApiException apiException) {
            Response response = apiException.getResponse();

            if (Response.Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
                throw new NotFoundException("Car with id: " + carId + " not found");
            } else {
                throw new InternalServerErrorException(apiException.getMessage());
            }
        } catch (ProcessingException processingException) {
            throw new InternalServerErrorException(processingException.getMessage());
        }
    }

    private CarsApi getCarsApi() {
        return RestClientBuilder.newBuilder()
                .baseUri(carsURI)
                .register(JwtAuthTokenProvider.class)
                .build(CarsApi.class);
    }

}
