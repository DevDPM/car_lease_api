package nl.sogeti.gatewayapi.resource;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.gatewayapi.dto.CustomerDto;

@Provider
public class CustomerResource implements CustomersApi {



    @Override
    public Response createCustomer(CustomerDto customerDto) {
        return null;
    }

    @Override
    public Response deleteCustomer(Integer id) {
        return null;
    }

    @Override
    public Response getAllCustomers() {
        return null;
    }

    @Override
    public Response getCustomer(Integer id) {
        return null;
    }

    @Override
    public Response updateCustomer(Integer id, CustomerDto customerDto) {
        return null;
    }
}
