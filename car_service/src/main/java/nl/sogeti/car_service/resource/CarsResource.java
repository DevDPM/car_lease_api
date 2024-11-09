package nl.sogeti.car_service.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.car_service.di.CarRepository;
import nl.sogeti.car_service.dto.CarDetails;
import nl.sogeti.car_service.dto.CarsDetails;
import nl.sogeti.car_service.dto.mapper.CarMapper;
import nl.sogeti.car_service.entity.CarEntity;

import java.net.URI;
import java.util.HashSet;
import java.util.List;


@Provider
public class CarsResource implements CarsApi {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

//    TEMPORARY CODE FOR GENERATING JWT
    {
        System.setProperty("smallrye.jwt.sign.key.location", "privateKey.pem");
        String token =
                Jwt.issuer("http://localhost:9999/")
                        .upn("daniel@example.com")
                        .groups(new HashSet<>(List.of("Fun-User")))
                        .sign();
        System.out.println(token);
    }

    @Inject
    public CarsResource(CarRepository carRepository,
                        CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    @RolesAllowed({ "Fun-User" })
    public Response get(Integer id) {
        CarEntity carEntity = getcarEntity(id);
        CarDetails carDetail = carMapper.toCarDetails(carEntity);

        return Response.ok(carDetail).build();
    }

    private CarEntity getcarEntity(Integer id) throws NotFoundException {
        return carRepository.findByIdOptional(id.longValue())
                            .orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    @RolesAllowed({ "Fun-User" })
    public Response update(Integer id, CarDetails carDetails) {
        CarEntity carEntity = carRepository.findById(id.longValue());
        carMapper.updateCarEntity(carEntity, carDetails);

        return Response.noContent().build();
    }

    @Override
    @Transactional
    @RolesAllowed({ "Fun-User" })
    public Response create(CarDetails carDetails) {
        CarEntity carEntity = carMapper.toCarEntity(carDetails);
        carRepository.persist(carEntity);

        if (!isCarPersistent(carEntity)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.created(URI.create("/cars/" + carEntity.getId())).build();
    }

    private boolean isCarPersistent(CarEntity carEntity) {
        return carRepository.isPersistent(carEntity);
    }

    @Override
    @Transactional
    @RolesAllowed({ "Fun-User" })
    public Response delete(Integer id) {
        boolean isCarDeleted = carRepository.deleteById(id.longValue());

        if (!isCarDeleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }

    @Override
    @RolesAllowed({ "Fun-User" })
    public Response getAll() {
        List<CarEntity> customerEntities = carRepository.findAll().list();
        CarsDetails customerDetails = carMapper.toCarsDetails(customerEntities);
        return Response.ok(customerDetails).build();
    }
}
