package com.fleetmanagement.adminservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "truck")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TruckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "truck_number", nullable = false, unique = true)
    private String truckNumber;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Column(name = "driver_contact")
    private String driverContact;

    @Column(name = "truck_type")
    private String truckType;

    @Column(name = "capacity")
    private Integer capacity;
}
