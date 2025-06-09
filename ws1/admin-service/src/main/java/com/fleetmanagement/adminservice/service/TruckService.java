package com.fleetmanagement.adminservice.service;




import java.util.List;

import com.fleetmanagement.adminservice.entity.TruckEntity;

public interface TruckService {
    TruckEntity registerTruck(TruckEntity truck);
    TruckEntity updateTruck(Long id, TruckEntity truck);
    TruckEntity getTruckById(Long id);
    List<TruckEntity> getAllTrucks();
    void deleteTruck(Long id);
}

