package com.cosmos.usersmanagementsystem.controller;
import com.cosmos.usersmanagementsystem.dto.*;
import com.cosmos.usersmanagementsystem.entity.OurUsers;
import com.cosmos.usersmanagementsystem.entity.Reservation;
import com.cosmos.usersmanagementsystem.service.DriverServices;
import com.cosmos.usersmanagementsystem.service.ReservationService;
import com.cosmos.usersmanagementsystem.service.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final ReservationService reservationService;
    private final DriverServices driverServices;
    private final UserServices userServices;
    @PostMapping("/add")
    public ResponseEntity<String> addReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            Reservation addedReservation = reservationService.addReservation(reservationDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Reservation added successfully with ID: " + addedReservation.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add reservation: " + e.getMessage());
        }
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Integer reservationId,
                                                            @RequestBody ReservationDTO updatedReservationDTO) {
        ReservationDTO updatedReservation = reservationService.updateReservation(reservationId, updatedReservationDTO);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/reservation")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@RequestBody OurUsers users) {
        List<ReservationDTO> reservations = reservationService.getAllReservations(users);
        return ResponseEntity.ok(reservations);
    }
    @GetMapping("/offers")
    public ResponseEntity<List<OffresDTO>> getAllOffres() {
        List<OffresDTO> offresList = driverServices.getAllOffres();
        return ResponseEntity.ok(offresList);
    }
    @GetMapping("/offersFiltre")
    public ResponseEntity<List<OffresDTO>> getOffresFiltered(@RequestParam String villeDep,
                                                             @RequestParam String villeArrv,
                                                             @RequestParam Date date
    ) {
        List<OffresDTO> offresList = userServices.getOffreFiltered(villeDep,villeArrv,date);
        return ResponseEntity.ok(offresList);
    }
}


