package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.AppointmentDTO;
import com.ecdms.ecdms.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/add-appointment")
    public ResponseEntity addAppointment(@RequestBody AppointmentDTO appointmentDTO){
        return appointmentService.addAppointment(appointmentDTO);
    }
    @PostMapping("/approve-appointment")
    public ResponseEntity approveAppointment(@RequestBody AppointmentDTO appointmentDTO){
        return  appointmentService.approveAppointment(appointmentDTO);
    }
    @GetMapping("/get-pending-appointment")
    public ResponseEntity getPendingAppointment(){
        return appointmentService.getPendingAppointment();
    }
    @GetMapping("/get-appointments-date-vise")
    public ResponseEntity getAppointmentDateVise(){
        return appointmentService.getAppointmentDateVise();
    }
    @PostMapping("/get-available-time-according-to-date")
    public ResponseEntity getAvailableTimeAccordingToDate(@RequestBody AppointmentDTO appointmentDTO){
        return appointmentService.getAvailableTimeAccordingToDate(appointmentDTO);
    }
        @GetMapping("/get-appointment-by-user")
    public ResponseEntity getAppointmentByUser(@RequestParam("userID") int userID){
        return appointmentService.getAppointmentByUser(userID);
    }
}
