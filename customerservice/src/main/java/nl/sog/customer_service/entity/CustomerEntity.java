package nl.sog.customerservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@ToString
@Entity
@Data
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "place")
    private String place;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

}
