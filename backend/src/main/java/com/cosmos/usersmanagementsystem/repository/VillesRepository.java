package com.cosmos.usersmanagementsystem.repository;

import com.cosmos.usersmanagementsystem.entity.Villes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillesRepository extends JpaRepository<Villes,Integer> {
}
