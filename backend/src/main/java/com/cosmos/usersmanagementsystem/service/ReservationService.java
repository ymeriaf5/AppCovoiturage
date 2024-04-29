package com.cosmos.usersmanagementsystem.service;

import com.cosmos.usersmanagementsystem.dto.OffresDTO;
import com.cosmos.usersmanagementsystem.dto.ReqRes;
import com.cosmos.usersmanagementsystem.dto.ReservationDTO;
import com.cosmos.usersmanagementsystem.entity.Offres;
import com.cosmos.usersmanagementsystem.entity.OurUsers;
import com.cosmos.usersmanagementsystem.entity.Reservation;
import com.cosmos.usersmanagementsystem.repository.ReservationRepository;
import com.cosmos.usersmanagementsystem.repository.UsersRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private Reservation mapToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        return reservation;
    }

    private Offres mapToEntity(OffresDTO offresDTO) {
        Offres offres = new Offres();
        offres.setId(offresDTO.getId());
        return offres;
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        return reservationDTO;
    }

    public Reservation addReservation(ReservationDTO reservationDTO) {
            Reservation reservation = mapToEntity(reservationDTO);
            return reservationRepository.save(reservation);
    }

    public ReservationDTO updateReservation(Integer reservationId, ReservationDTO updatedReservationDTO) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        ReservationDTO reservationDTO = new ReservationDTO();
        try{
            if (optionalReservation.isPresent()) {
                Reservation existingReservation = optionalReservation.get();
                existingReservation.setOffre(mapToEntity(updatedReservationDTO.getOffre()));
                existingReservation.setUsers(updatedReservationDTO.getUsers());
                Reservation updatedReservation = reservationRepository.save(existingReservation);
                reservationDTO = mapToDTO(updatedReservation);
                reservationDTO.setMessage("Successfully Updated Reservation");
                reservationDTO.setStatusCode(200);
            }
            else {
                reservationDTO.setStatusCode(404);
                reservationDTO.setMessage("Reservation not found");
            }
        }
        catch (Exception e){
            reservationDTO.setStatusCode(500);
            reservationDTO.setMessage(e.getMessage());
        }
        return reservationDTO;
    }

    public boolean deleteReservation(Integer reservationId) {
        ReservationDTO reservationDTO = new ReservationDTO();
        try{
            if (reservationRepository.existsById(reservationId)) {
                reservationRepository.deleteById(reservationId);
                reservationDTO.setMessage("Successfully Deleted Reservation");
                reservationDTO.setStatusCode(200);
                return true;
            }
            else {
                reservationDTO.setStatusCode(404);
                reservationDTO.setMessage("Reservation not Deleted");
            }
        }
        catch (Exception e){
            reservationDTO.setStatusCode(500);
            reservationDTO.setMessage(e.getMessage());
        }
        return false;
    }

    public List<ReservationDTO> getAllReservations(OurUsers user) {
        List<Reservation> reservationList = reservationRepository.findByUsers(user);
        return reservationList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}

