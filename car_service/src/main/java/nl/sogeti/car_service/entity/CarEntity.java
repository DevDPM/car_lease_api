package nl.sogeti.car_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Entity
@Data
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "version")
    private String version;

    @Column(name = "numberOfDoors")
    private Integer numberOfDoors;

    @Column(name = "carbonDioxideEmission")
    private String carbonDioxideEmission;

    @Column(name = "gross")
    private BigDecimal gross;

    @Column(name = "nett")
    private BigDecimal nett;

}
