package nl.sogeti.gatewayapi.dto.mapper;


import nl.sogeti.gatewayapi.di.carservice.dto.CarDetails;
import nl.sogeti.gatewayapi.dto.CarDto;
import nl.sogeti.gatewayapi.dto.CarsDto;
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
    CarDto toCarDto(CarDetails carDetails);

    @Mappings(value = {
            @Mapping(source = "make", target = "make"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "version", target = "version"),
            @Mapping(source = "numberOfDoors", target = "numberOfDoors"),
            @Mapping(source = "carbonDioxideEmission", target = "carbonDioxideEmission"),
            @Mapping(source = "gross", target = "gross"),
            @Mapping(source = "nett", target = "nett")
    })
    CarDetails toCarDetails(CarDto carDto);

    @Mappings(value = {
            @Mapping(source = "make", target = "make"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "version", target = "version"),
            @Mapping(source = "numberOfDoors", target = "numberOfDoors"),
            @Mapping(source = "carbonDioxideEmission", target = "carbonDioxideEmission"),
            @Mapping(source = "gross", target = "gross"),
            @Mapping(source = "nett", target = "nett")
    })
    void updateCarDetails(@MappingTarget CarDetails carDetails, CarDto carDto);

    default CarsDto toCarsDetails(List<CarDetails> carsDetails) {
        List<CarDto> carDetailsList = mapToCarDetailsList(carsDetails);
        CarsDto carsDto = new CarsDto();
        carsDto.setCars(carDetailsList);
        return carsDto;
    }

    private List<CarDto> mapToCarDetailsList(List<CarDetails> carsDetails) {
        return carsDetails.stream()
                          .map(this::toCarDto)
                          .toList();
    }
}
