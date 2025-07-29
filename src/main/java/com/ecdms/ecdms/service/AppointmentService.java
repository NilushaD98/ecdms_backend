package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.AppointmentDTO;
import org.springframework.http.ResponseEntity;

public interface AppointmentService {
    ResponseEntity addAppointment(AppointmentDTO appointmentDTO);

    ResponseEntity approveAppointment(AppointmentDTO appointmentDTO);

    ResponseEntity getAvailableTimeAccordingToDate(AppointmentDTO appointmentDTO);

    ResponseEntity getPendingAppointment();

    ResponseEntity getAppointmentDateVise();

    ResponseEntity getAppointmentByUser(int userID);
}
