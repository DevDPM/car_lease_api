package nl.sogeti.car_service.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.car_service.di.CarRepository;
import nl.sogeti.car_service.dto.CarDetails;
import nl.sogeti.car_service.dto.mapper.CarMapper;
import nl.sogeti.car_service.entity.CarEntity;

import java.util.HashSet;
import java.util.List;

@Provider
public class CarsResource implements CarsApi {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

//    TEMPORARY CODE FOR GENERATING JWT
//    {
//        System.setProperty("smallrye.jwt.sign.key.location", "privateKey.pem");
//        String token =
//                Jwt.issuer("http://localhost:8001/")
//                        .upn("daniel@example.com")
//                        .groups(new HashSet<>(List.of("Fun-User")))
//                        .sign();
//        System.out.println(token);
//    }

    @Inject
    public CarsResource(CarRepository carRepository,
                        CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public Response create(CarDetails carDetails) {
        return null;
    }

    @Override
    public Response delete(Integer id) {
        return null;
    }

    @Override
    @RolesAllowed({ "Fun-User" })
    public Response get(Integer id) {
        CarEntity carEntity = getcarEntity(id);
        CarDetails carDetail = carMapper.toCarDetails(carEntity);

        return Response.ok(carDetail).build();
    }

    @Override
    public Response getAll() {
        return null;
    }

    @Override
    public Response update(Integer id, CarDetails carDetails) {
        return null;
    }

    private CarEntity getcarEntity(Integer id) throws NotFoundException {
        return carRepository.findByIdOptional(id.longValue())
                                 .orElseThrow(NotFoundException::new);
    }
}
