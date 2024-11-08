package nl.sogeti.customer_service.dto.mapper;

import nl.sogeti.customer_service.dto.CustomerDetail;
import nl.sogeti.customer_service.dto.CustomerDetails;
import nl.sogeti.customer_service.entity.CustomerEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Mappings(value = {
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "street", target = "street"),
            @Mapping(source = "houseNumber", target = "houseNumber"),
            @Mapping(source = "zipCode", target = "zipCode"),
            @Mapping(source = "place", target = "place"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
    })
    CustomerDetail toCustomerDetail(CustomerEntity customerEntity);

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "street", target = "street"),
            @Mapping(source = "houseNumber", target = "houseNumber"),
            @Mapping(source = "zipCode", target = "zipCode"),
            @Mapping(source = "place", target = "place"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
    })
    CustomerEntity toCustomerEntity(CustomerDetail customerDetail);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "street", target = "street"),
            @Mapping(source = "houseNumber", target = "houseNumber"),
            @Mapping(source = "zipCode", target = "zipCode"),
            @Mapping(source = "place", target = "place"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
    })
    void updateCustomerEntity(@MappingTarget CustomerEntity customerEntity, CustomerDetail customerDetail);

    default CustomerDetails toCustomerDetails(List<CustomerEntity> customerEntities) {
        List<CustomerDetail> customerDetailList = customerEntities.stream().map(this::toCustomerDetail).toList();

        CustomerDetails customerDetailWrapper = new CustomerDetails();
        customerDetailWrapper.setCustomers(customerDetailList);
        return customerDetailWrapper;
    }
}
