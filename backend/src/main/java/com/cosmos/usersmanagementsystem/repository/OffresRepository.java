package com.cosmos.usersmanagementsystem.repository;

import com.cosmos.usersmanagementsystem.entity.Offres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffresRepository extends JpaRepository<Offres,Integer> {
}
