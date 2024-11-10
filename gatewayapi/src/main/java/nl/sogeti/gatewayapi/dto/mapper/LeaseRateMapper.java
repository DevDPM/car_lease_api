package nl.sogeti.gatewayapi.dto.mapper;


import nl.sogeti.gatewayapi.di.carservice.dto.CarDetails;
import nl.sogeti.gatewayapi.di.leaserateservice.dto.LeaseRateDetails;
import nl.sogeti.gatewayapi.dto.CarDto;
import nl.sogeti.gatewayapi.dto.CarsDto;
import nl.sogeti.gatewayapi.dto.LeaseRateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface LeaseRateMapper {

    @Mappings(value = {
            @Mapping(source = "leaseRate", target = "leaseRate")
    })
    LeaseRateDto toLeaseRateDto(LeaseRateDetails leaseRateDetails);
}
