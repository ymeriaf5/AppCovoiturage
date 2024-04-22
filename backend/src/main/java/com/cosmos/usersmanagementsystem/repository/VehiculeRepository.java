package com.cosmos.usersmanagementsystem.repository;

import com.cosmos.usersmanagementsystem.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule,Integer> {
}
