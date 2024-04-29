package com.cosmos.usersmanagementsystem.repository;

import com.cosmos.usersmanagementsystem.entity.Offres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OffresRepository extends JpaRepository<Offres,Integer> {
    List<Offres> findOffresByVille_departAndVille_arrivAndHeure_depart(String villes_dep, String ville_arriv, Date date);
}
