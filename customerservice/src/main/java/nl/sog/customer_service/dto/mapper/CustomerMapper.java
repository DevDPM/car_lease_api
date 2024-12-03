package nl.sog.customerservice.dto.mapper;

import nl.sog.customerservice.dto.CustomerDetails;
import nl.sog.customerservice.dto.CustomersDetails;
import nl.sog.customerservice.entity.CustomerEntity;
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
    void updateCustomerEntity(@MappingTarget CustomerEntity customerEntity, CustomerDetails customerDetails);

    default CustomersDetails toCustomersDetails(List<CustomerEntity> customerEntities) {
        List<CustomerDetails> customerDetailsList = mapToCustomerDetailsList(customerEntities);
        CustomersDetails customersDetails = new CustomersDetails();
        customersDetails.setCustomers(customerDetailsList);
        return customersDetails;
    }

    private List<CustomerDetails> mapToCustomerDetailsList(List<CustomerEntity> customerEntities) {
        return customerEntities.stream()
                               .map(this::toCustomerDetail)
                               .toList();
    }
}
