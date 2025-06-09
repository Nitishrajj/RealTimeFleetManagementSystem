package com.fleetmanagement.adminservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.fleetmanagement.adminservice.entity.TruckEntity;

@Repository
public interface TruckRepository extends JpaRepository<TruckEntity, Long> {
    Optional<TruckEntity> findByTruckNumber(String truckNumber);
}
