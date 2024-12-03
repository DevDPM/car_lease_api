package nl.sog.gatewayapi.di.leaserateservice.client;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import nl.sog.gatewayapi.di.common.provider.JwtAuthTokenProvider;
import nl.sog.gatewayapi.di.leaserateservice.dto.LeaseRateDetails;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.math.BigDecimal;
import java.net.URI;

@Dependent
public class LeaseRateClient {

    private final URI leaseRateURI = URI.create("http://localhost:8002/");

    public LeaseRateDetails getLeaserateDetails(Integer carId, Integer mileage, Integer duration, BigDecimal interestrate) {
        try {
            return getLeaseRateApi().get(carId, mileage, duration, interestrate);
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

    private LeaseRateApi getLeaseRateApi() {
        return RestClientBuilder.newBuilder()
                .baseUri(leaseRateURI)
                .register(JwtAuthTokenProvider.class)
                .build(LeaseRateApi.class);
    }
}
