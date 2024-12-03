package nl.sog.gatewayapi.di.customerservice.client;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import nl.sog.gatewayapi.di.common.provider.JwtAuthTokenProvider;
import nl.sog.gatewayapi.di.customerservice.ApiException;
import nl.sog.gatewayapi.di.customerservice.CustomerApi;
import nl.sog.gatewayapi.di.customerservice.dto.CustomerDetails;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.List;

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

    public Long createCustomer(CustomerDetails customerDetails) {
        try (Response response = getCustomerApi().create(customerDetails)) {
            return extractCustomerId(response);
        } catch (ApiException | ProcessingException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private static Long extractCustomerId(Response response) {
        String responsePath = response.getLocation().getPath();
        String[] paths = responsePath.split("/");
        String customerIdAsString = paths[paths.length - 1];
        try {
            return Long.valueOf(customerIdAsString);
        } catch (NumberFormatException e) {
            throw new InternalServerErrorException("Unable to extract customer id: " + customerIdAsString + " from: " + responsePath);
        }
    }

    public int deleteCustomer(Integer id) {
        try (Response response = getCustomerApi().delete(id)) {
            return response.getStatus();
        } catch (ApiException apiException) {
            Response response = apiException.getResponse();

            if (Response.Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
                throw new NotFoundException("Customer with id: " + id + " not found");
            }  else {
                throw new InternalServerErrorException(apiException.getMessage());
            }

        } catch (ProcessingException processingException) {
            throw new InternalServerErrorException(processingException.getMessage());
        }
    }

    public List<CustomerDetails> getAllCustomers() {
        try {
            return getCustomerApi().getAll().getCustomers();
        } catch (ApiException | ProcessingException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public int updateCustomer(Integer id, CustomerDetails customerDetails) {
        try (Response response = getCustomerApi().update(id, customerDetails)) {
            return response.getStatus();
        } catch (ApiException | ProcessingException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
