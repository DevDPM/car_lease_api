package nl.sogeti.gatewayapi.di.customerservice.client;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import nl.sogeti.gatewayapi.di.common.provider.JwtAuthTokenProvider;
import nl.sogeti.gatewayapi.di.customerservice.ApiException;
import nl.sogeti.gatewayapi.di.customerservice.CustomerApi;
import nl.sogeti.gatewayapi.di.customerservice.dto.CustomerDetails;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

@Dependent
public class CustomerClient {

    private final URI customerURI = URI.create("http://localhost:8000/");

    public CustomerDetails getCustomerDetails(Integer carId) {
        try {
            return getCustomerApi().get(carId);
        } catch (ApiException apiException) {
            Response response = apiException.getResponse();

            if (Response.Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
                throw new NotFoundException("Customer with id: " + carId + " not found");
            } else {
                throw new InternalServerErrorException(apiException.getMessage());
            }
        } catch (ProcessingException processingException) {
            throw new InternalServerErrorException(processingException.getMessage());
        }
    }

    private CustomerApi getCustomerApi() {
        return RestClientBuilder.newBuilder()
                .baseUri(customerURI)
                .register(JwtAuthTokenProvider.class)
                .build(CustomerApi.class);
    }
}
