package nl.sogeti.gatewayapi.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.gatewayapi.di.customerservice.client.CustomerClient;
import nl.sogeti.gatewayapi.di.customerservice.dto.CustomerDetails;
import nl.sogeti.gatewayapi.dto.CustomerDto;
import nl.sogeti.gatewayapi.dto.CustomersDto;
import nl.sogeti.gatewayapi.dto.mapper.CustomerMapper;
import java.net.URI;
import java.util.List;

@Provider
public class CustomerResource implements CustomersApi {

    private final CustomerClient customerClient;
    private final CustomerMapper customerMapper;

    @Inject
    public CustomerResource(CustomerClient customerClient, CustomerMapper customerMapper) {
        this.customerClient = customerClient;
        this.customerMapper = customerMapper;
    }

    @Override
    public Response createCustomer(CustomerDto customerDto) {
        CustomerDetails customerDetails = customerMapper.toCustomerDetails(customerDto);
        Long customerId = customerClient.createCustomer(customerDetails);
        return Response.created(URI.create("/customers/" + customerId)).build();
    }

    @Override
    public Response deleteCustomer(Integer id) {
        int statusCode = customerClient.deleteCustomer(id);

        if (statusCode == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException("Unknown status code: " + statusCode);
        }
    }

    @Override
    public Response getAllCustomers() {
        List<CustomerDetails> customersDetails = customerClient.getAllCustomers();
        CustomersDto customersDto = customerMapper.toCustomersDto(customersDetails);
        return Response.ok(customersDto).build();
    }

    @Override
    public Response getCustomer(Integer id) {
        CustomerDetails customerDetails = customerClient.getCustomerDetails(id);
        CustomerDto customerDto = customerMapper.toCustomerDto(customerDetails);
        return Response.ok(customerDto).build();
    }

    @Override
    public Response updateCustomer(Integer id, CustomerDto customerDto) {
        CustomerDetails customerDetails = customerMapper.toCustomerDetails(customerDto);
        int statusCode = customerClient.updateCustomer(id, customerDetails);

        if (statusCode == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException("Unknown status code: " + statusCode);
        }
    }
}
