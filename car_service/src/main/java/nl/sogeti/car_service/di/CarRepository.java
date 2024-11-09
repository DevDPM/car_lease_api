package nl.sogeti.car_service.di;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import nl.sogeti.car_service.entity.CarEntity;

@ApplicationScoped
public class CarRepository implements PanacheRepositoryBase<CarEntity, Long> {

}
