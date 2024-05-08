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

    public Offres addOffre(OffresDTO offreDTO) {
            Offres offre = mapToEntity(offreDTO);
            return offresRepository.save(offre);
    }

    public OffresDTO updateOffres(Integer offreId, Offres offres) {
        OffresDTO offresDTO = new OffresDTO();
        try {
            Optional<Offres> optionalOffres = offresRepository.findById(offreId);
            if (optionalOffres.isPresent()) {
                Offres existantOffre = optionalOffres.get();
                existantOffre.setVilleDepart(offres.getVilleDepart());
                existantOffre.setVilleArriv(offres.getVilleArriv());
                existantOffre.setHeureDepart(offres.getHeureDepart());
                existantOffre.setHeureDarriv(offres.getHeureDarriv());
                existantOffre.setPrix(offres.getPrix());
                existantOffre.setPlaceDispo(offres.getPlaceDispo());
                existantOffre.setPlaceInitiale(offres.getPlaceInitiale());
                existantOffre.setStatus(offres.getStatus());
                Offres savedOffre = offresRepository.save(existantOffre);
                offresDTO = mapToDTO(savedOffre);
                offresDTO.setStatusCode(200);
                offresDTO.setMessage("Successfully Updated Offre");
            }
            else {
                offresDTO.setStatusCode(404);
                offresDTO.setMessage("Offer not found");
            }
        }catch (Exception e){
            offresDTO.setStatusCode(500);
            offresDTO.setMessage(e.getMessage());
        }
        return offresDTO;
    }


    public boolean deleteOffre(Integer offreId) {
        OffresDTO offresDTO = new OffresDTO();
        try {
            if (offresRepository.existsById(offreId)) {
                offresRepository.deleteById(offreId);
                offresDTO.setStatusCode(200);
                offresDTO.setMessage("Successfully Deleted Offer");
                return true;
            }
        }catch (Exception e){
            offresDTO.setStatusCode(500);
            offresDTO.setMessage("Error occurred: " + e.getMessage());
        }
        return false;
    }
    public List<OffresDTO> getAllOffres() {
        List<Offres> offresList = offresRepository.findAll();
        return offresList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }
}

