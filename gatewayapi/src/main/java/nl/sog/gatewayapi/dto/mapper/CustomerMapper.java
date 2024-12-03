package nl.sog.gatewayapi.dto.mapper;

import nl.sog.gatewayapi.di.customerservice.dto.CustomerDetails;
import nl.sog.gatewayapi.dto.CustomerDto;
import nl.sog.gatewayapi.dto.CustomersDto;
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
    CustomerDto toCustomerDto(CustomerDetails customerDetails);

    @Mappings(value = {
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "street", target = "street"),
            @Mapping(source = "houseNumber", target = "houseNumber"),
            @Mapping(source = "zipCode", target = "zipCode"),
            @Mapping(source = "place", target = "place"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
    })
    CustomerDetails toCustomerDetails(CustomerDto customerDto);

    default CustomersDto toCustomersDto(List<CustomerDetails> customerDetailsList) {
        List<CustomerDto> customerDtoList = mapToCustomerDtoList(customerDetailsList);
        CustomersDto customersDetails = new CustomersDto();
        customersDetails.setCustomers(customerDtoList);
        return customersDetails;
    }

    private List<CustomerDto> mapToCustomerDtoList(List<CustomerDetails> customerEntities) {
        return customerEntities.stream()
                               .map(this::toCustomerDto)
                               .toList();
    }
}
