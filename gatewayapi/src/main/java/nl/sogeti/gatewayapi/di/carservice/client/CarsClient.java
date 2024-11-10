package nl.sogeti.gatewayapi.di.carservice.client;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import nl.sogeti.gatewayapi.di.carservice.exception.ApiException;
import nl.sogeti.gatewayapi.di.carservice.dto.CarDetails;
import nl.sogeti.gatewayapi.di.common.provider.JwtAuthTokenProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.List;

@Dependent
public class CarsClient {

    private final URI carsURI = URI.create("http://localhost:8001/");

    public Long createCar(CarDetails carDetails) {
        try (Response response = getCarsApi().create(carDetails)) {
            return extractCarId(response);
        } catch (ApiException | ProcessingException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private static Long extractCarId(Response response) {
        String responsePath = response.getLocation().getPath();
        String[] paths = responsePath.split("/");
        String carIdAsString = paths[paths.length - 1];
        Long carId = Long.getLong(carIdAsString, null);
        if (carId == null) {
            throw new InternalServerErrorException("Unable to extract car id: " + carIdAsString + " from: " + responsePath);
        }
        return carId;
    }

    public CarDetails getCarDetails(Integer carId) {
        try {
            return getCarsApi().get(carId);
        } catch (ApiException apiException) {
            Response response = apiException.getResponse();

            if (Response.Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
                throw new NotFoundException("Car with id: " + carId + " not found");
            }  else {
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

    /**
     * Delete car by ID
     * @Return client status code as int value
     * */

    public int deleteCar(Integer id) {
        try (Response response = getCarsApi().delete(id)) {
            return response.getStatus();
        } catch (ApiException apiException) {
            Response response = apiException.getResponse();

            if (Response.Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
                throw new NotFoundException("Car with id: " + id + " not found");
            }  else {
                throw new InternalServerErrorException(apiException.getMessage());
            }

        } catch (ProcessingException processingException) {
            throw new InternalServerErrorException(processingException.getMessage());
        }
    }

    public List<CarDetails> getAllCars() {
        try {
            return getCarsApi().getAll().getCars();
        } catch (ApiException | ProcessingException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public int updateCar(Integer id, CarDetails carDetails) {
        try (Response response = getCarsApi().update(id, carDetails)) {
            return response.getStatus();
        } catch (ApiException | ProcessingException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
