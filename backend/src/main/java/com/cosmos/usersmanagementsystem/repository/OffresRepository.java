package com.cosmos.usersmanagementsystem.repository;

import com.cosmos.usersmanagementsystem.entity.Offres;
import com.cosmos.usersmanagementsystem.entity.Villes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OffresRepository extends JpaRepository<Offres,Integer> {
    List<Offres> findOffresByVilleDepartAndVilleArrivAndHeureDepart(Villes villedepart, Villes villearriv, Date heuredepart);
}
