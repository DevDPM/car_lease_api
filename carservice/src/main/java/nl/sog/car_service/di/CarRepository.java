package nl.sog.carservice.di;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import nl.sog.carservice.entity.CarEntity;

@ApplicationScoped
public class CarRepository implements PanacheRepositoryBase<CarEntity, Long> {

}
