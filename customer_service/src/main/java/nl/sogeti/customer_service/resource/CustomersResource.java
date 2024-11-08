package nl.sogeti.customer_service.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.customer_service.di.CustomerRepository;
import nl.sogeti.customer_service.dto.CustomerDetail;
import nl.sogeti.customer_service.dto.CustomerDetails;
import nl.sogeti.customer_service.dto.mapper.CustomerMapper;
import nl.sogeti.customer_service.entity.CustomerEntity;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.List;

@Provider
@RolesAllowed({ "User" })
public class CustomersResource implements CustomersApi {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Inject
    public CustomersResource(CustomerRepository customerRepository,
                             CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Response get(Integer id) {
        CustomerEntity customerEntity = getCustomerEntity(id);
        CustomerDetail customerDetail = customerMapper.toCustomerDetail(customerEntity);

        return Response.ok(customerDetail).build();
    }

    private CustomerEntity getCustomerEntity(Integer id) throws NotFoundException {
        return customerRepository.findByIdOptional(id.longValue())
                                 .orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public Response update(Integer id, CustomerDetail customerDetail) {
        CustomerEntity customerEntity = customerRepository.findById(id.longValue());
        customerMapper.updateCustomerEntity(customerEntity, customerDetail);

        return Response.noContent().build();
    }

    @Override
    @Transactional
    public Response create(CustomerDetail customerDetail) {
        CustomerEntity customerEntity = customerMapper.toCustomerEntity(customerDetail);
        customerRepository.persist(customerEntity);

        if (!customerRepository.isPersistent(customerEntity)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.created(URI.create("/customers/" + customerEntity.getId())).build();
    }

    @Override
    public Response delete(Integer id) {
        boolean isCustomerDeleted = customerRepository.deleteById(id.longValue());

        if (!isCustomerDeleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }

    @Override
    public Response getAll() {
        List<CustomerEntity> customerEntities = customerRepository.findAll().list();
        CustomerDetails customerDetails = customerMapper.toCustomerDetails(customerEntities);
        return Response.ok(customerDetails).build();
    }
}
