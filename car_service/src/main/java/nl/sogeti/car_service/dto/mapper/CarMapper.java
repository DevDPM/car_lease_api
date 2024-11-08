package nl.sogeti.car_service.dto.mapper;

import nl.sogeti.car_service.dto.CarDetail;
import nl.sogeti.car_service.entity.CarEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface CarMapper {

    @Mappings(value = {
            @Mapping(source = "make", target = "make"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "version", target = "version"),
            @Mapping(source = "numberOfDoors", target = "numberOfDoors"),
            @Mapping(source = "carbonDioxideEmission", target = "carbonDioxideEmission"),
            @Mapping(source = "gross", target = "gross"),
            @Mapping(source = "nett", target = "nett"),
    })
    CarDetail toCarDetails(CarEntity carEntity);
}
