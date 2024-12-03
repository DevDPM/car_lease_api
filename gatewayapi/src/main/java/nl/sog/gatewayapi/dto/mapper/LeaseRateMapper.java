package nl.sog.gatewayapi.dto.mapper;


import nl.sog.gatewayapi.di.leaserateservice.dto.LeaseRateDetails;
import nl.sog.gatewayapi.dto.LeaseRateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface LeaseRateMapper {

    @Mappings(value = {
            @Mapping(source = "leaseRate", target = "leaseRate")
    })
    LeaseRateDto toLeaseRateDto(LeaseRateDetails leaseRateDetails);
}
