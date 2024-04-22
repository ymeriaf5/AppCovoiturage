package com.cosmos.usersmanagementsystem.dto;

import com.cosmos.usersmanagementsystem.entity.Villes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class OffresDTO {
    private Integer id;
    private Integer driverId;
    private Integer villeDepartId;
    private Integer villeArrivId;
    private Date heureDepart;
    private Date heureArriv;
    private Double prix;
    private int placeDispo;
    private int placeInitiale;
    private Boolean status;
}
