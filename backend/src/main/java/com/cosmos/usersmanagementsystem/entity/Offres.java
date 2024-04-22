package com.cosmos.usersmanagementsystem.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offres {
    @Id
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    private OurUsers driver;
    @OneToOne(fetch = FetchType.LAZY)
    private Villes ville_depart;
    @OneToOne(fetch = FetchType.LAZY)
    private Villes ville_arriv;
    private Date heure_depart;
    private Date heure_arriv;
    private Double prix;
    private int placeDispo;
    private int placeInitiale;
    private Boolean status;
}