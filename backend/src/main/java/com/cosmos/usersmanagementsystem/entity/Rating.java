package com.cosmos.usersmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private OurUsers client;
    @OneToOne(fetch = FetchType.LAZY)
    private Offres offres;
    @ManyToOne(fetch = FetchType.LAZY)
    private OurUsers driver;
}
