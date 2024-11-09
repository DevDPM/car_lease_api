package nl.sogeti.customer_service.dto.mapper;

import nl.sogeti.customer_service.dto.CustomerDetails;
import nl.sogeti.customer_service.dto.CustomersDetails;
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
    CustomerDetails toCustomerDetail(CustomerEntity customerEntity);

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
    CustomerEntity toCustomerEntity(CustomerDetails customerDetail);

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
    void updateCustomerEntity(@MappingTarget CustomerEntity customerEntity, CustomerDetails customerDetail);

    default CustomersDetails toCustomerDetails(List<CustomerEntity> customerEntities) {
        List<CustomerDetails> customerDetailList = customerEntities.stream().map(this::toCustomerDetail).toList();

        CustomersDetails customerDetailWrapper = new CustomersDetails();
        customerDetailWrapper.setCustomers(customerDetailList);
        return customerDetailWrapper;
    }
}
