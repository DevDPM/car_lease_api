package nl.sog.customerservice.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sog.customerservice.di.CustomerRepository;
import nl.sog.customerservice.dto.CustomerDetails;
import nl.sog.customerservice.dto.CustomersDetails;
import nl.sog.customerservice.dto.mapper.CustomerMapper;
import nl.sog.customerservice.entity.CustomerEntity;

import java.net.URI;
import java.util.HashSet;
import java.util.List;

@Provider
public class CustomersResource implements CustomersApi {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

//    TEMPORARY CODE FOR GENERATING JWT
    {
        System.setProperty("smallrye.jwt.sign.key.location", "privateKey.pem");
        String token =
                Jwt.issuer("http://localhost:9999/")
                        .upn("daniel@example.com")
                        .groups(new HashSet<>(List.of("Fun-User")))
                        .sign();
        System.out.println(token);
    }

    @Inject
    public CustomersResource(CustomerRepository customerRepository,
                             CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @RolesAllowed({ "Fun-User" })
    public Response get(Integer id) {
        CustomerEntity customerEntity = getCustomerEntity(id);
        CustomerDetails customerDetail = customerMapper.toCustomerDetail(customerEntity);

        return Response.ok(customerDetail).build();
    }

    private CustomerEntity getCustomerEntity(Integer id) throws NotFoundException {
        return customerRepository.findByIdOptional(id.longValue())
                                 .orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    @RolesAllowed({ "Fun-User" })
    public Response update(Integer id, CustomerDetails customerDetails) {
        CustomerEntity customerEntity = getCustomerEntity(id);
        customerMapper.updateCustomerEntity(customerEntity, customerDetails);

        return Response.noContent().build();
    }

    @Override
    @Transactional
    @RolesAllowed({ "Fun-User" })
    public Response create(CustomerDetails customerDetails) {
        CustomerEntity customerEntity = customerMapper.toCustomerEntity(customerDetails);
        customerRepository.persist(customerEntity);

        if (!customerRepository.isPersistent(customerEntity)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.created(URI.create("/customers/" + customerEntity.getId())).build();
    }

    @Override
    @Transactional
    @RolesAllowed({ "Fun-User" })
    public Response delete(Integer id) {
        boolean isCustomerDeleted = customerRepository.deleteById(id.longValue());

        if (!isCustomerDeleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }

    @Override
    @RolesAllowed({ "Fun-User" })
    public Response getAll() {
        List<CustomerEntity> customerEntities = customerRepository.findAll().list();
        CustomersDetails customerDetails = customerMapper.toCustomersDetails(customerEntities);
        return Response.ok(customerDetails).build();
    }
}
