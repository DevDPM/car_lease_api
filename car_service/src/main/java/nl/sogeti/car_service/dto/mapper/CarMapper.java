package nl.sogeti.car_service.dto.mapper;

import nl.sogeti.car_service.dto.CarDetails;
import nl.sogeti.car_service.dto.CarsDetails;
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
            @Mapping(source = "nett", target = "nett")
    })
    CarDetails toCarDetails(CarEntity carEntity);

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "make", target = "make"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "version", target = "version"),
            @Mapping(source = "numberOfDoors", target = "numberOfDoors"),
            @Mapping(source = "carbonDioxideEmission", target = "carbonDioxideEmission"),
            @Mapping(source = "gross", target = "gross"),
            @Mapping(source = "nett", target = "nett")
    })
    CarEntity toCarEntity(CarDetails carDetails);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "make", target = "make"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "version", target = "version"),
            @Mapping(source = "numberOfDoors", target = "numberOfDoors"),
            @Mapping(source = "carbonDioxideEmission", target = "carbonDioxideEmission"),
            @Mapping(source = "gross", target = "gross"),
            @Mapping(source = "nett", target = "nett")
    })
    void updateCarEntity(@MappingTarget CarEntity carEntity, CarDetails carDetails);

    default CarsDetails toCarsDetails(List<CarEntity> carEntities) {
        List<CarDetails> carDetailsList = mapToCarDetailsList(carEntities);
        CarsDetails carsDetails = new CarsDetails();
        carsDetails.setCars(carDetailsList);
        return carsDetails;
    }

    private List<CarDetails> mapToCarDetailsList(List<CarEntity> carEntities) {
        return carEntities.stream()
                          .map(this::toCarDetails)
                          .toList();
    }
}
