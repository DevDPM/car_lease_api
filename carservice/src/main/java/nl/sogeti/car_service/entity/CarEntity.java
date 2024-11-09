package nl.sogeti.carservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate make;

    @Column(name = "model")
    private String model;

    @Column(name = "version")
    private String version;

    @Column(name = "numberOfDoors")
    private Integer numberOfDoors;

    @Column(name = "carbonDioxideEmission")
    private BigDecimal carbonDioxideEmission;

    @Column(name = "gross")
    private BigDecimal gross;

    @Column(name = "nett")
    private BigDecimal nett;

}
