package com.cosmos.usersmanagementsystem.service;

import com.cosmos.usersmanagementsystem.dto.OffresDTO;
import com.cosmos.usersmanagementsystem.dto.ReqRes;
import com.cosmos.usersmanagementsystem.entity.Offres;
import com.cosmos.usersmanagementsystem.entity.OurUsers;
import com.cosmos.usersmanagementsystem.repository.OffresRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DriverServices {
    private final OffresRepository offresRepository;

    public Offres addOffre(OffresDTO offreDTO) {
        Offres offre = mapToEntity(offreDTO);
        return offresRepository.save(offre);
    }

    public OffresDTO updateOffres(Integer offreId, Offres offres) {
        OffresDTO offresDTO = new OffresDTO();
        Optional<Offres> optionalOffres = offresRepository.findById(offreId);
        if (optionalOffres.isPresent()) {
            Offres existantOffre = optionalOffres.get();
            existantOffre.setVille_depart(offres.getVille_depart());
            existantOffre.setVille_arriv(offres.getVille_arriv());
            existantOffre.setHeure_depart(offres.getHeure_depart());
            existantOffre.setHeure_arriv(offres.getHeure_arriv());
            existantOffre.setPrix(offres.getPrix());
            existantOffre.setPlaceDispo(offres.getPlaceDispo());
            existantOffre.setPlaceInitiale(offres.getPlaceInitiale());
            existantOffre.setStatus(offres.getStatus());
            Offres savedOffre = offresRepository.save(existantOffre);
            offresDTO = mapToDTO(savedOffre);
        }
        return offresDTO;
    }

    private Offres mapToEntity(OffresDTO offreDTO) {
        Offres offre = new Offres();
        offre.setId(offreDTO.getId());
        return offre;
    }

    private OffresDTO mapToDTO(Offres offres) {
        OffresDTO offresDTO = new OffresDTO();
        offresDTO.setId(offres.getId());
        return offresDTO;
    }
    public boolean deleteOffre(Integer offreId) {
        if (offresRepository.existsById(offreId)) {
            offresRepository.deleteById(offreId);
            return true;
        }
        return false; // Handle not found case
    }
    public List<OffresDTO> getAllOffres() {
        List<Offres> offresList = offresRepository.findAll();
        return offresList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }
}

