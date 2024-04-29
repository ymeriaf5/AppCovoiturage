package com.cosmos.usersmanagementsystem.dto;

import com.cosmos.usersmanagementsystem.entity.OurUsers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ReservationDTO {
    private int statusCode;
    private String error;
    private String message;
    private Integer id;
    private OffresDTO offre;
    private List<OurUsers> users;
}