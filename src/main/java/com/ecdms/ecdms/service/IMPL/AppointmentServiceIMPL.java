package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.AppointmentDTO;
import com.ecdms.ecdms.entity.Appointment;
import com.ecdms.ecdms.entity.User;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.AppointmentRepository;
import com.ecdms.ecdms.repository.UserRepository;
import com.ecdms.ecdms.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceIMPL implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserServiceIMPL userServiceIMPL;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity addAppointment(AppointmentDTO appointmentDTO) {
        try {
            Appointment appointment = new Appointment(
                    appointmentDTO.getStudentID(),
                    appointmentDTO.getReason(),
                    appointmentDTO.getScheduledTime(),
                    false
            );
            appointmentRepository.save(appointment);
            return new ResponseEntity(new StandardResponse(true,"Appointment added."), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Appointment add unsuccessful.");
        }
    }

    @Override
    public ResponseEntity approveAppointment(AppointmentDTO appointmentDTO) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' hh:mm a");
            String formattedDate = appointmentDTO.getScheduledTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .format(formatter);
            Optional<Appointment> byId = appointmentRepository.findById(appointmentDTO.getStudentID());
            byId.get().setConfirmed(true);
            appointmentRepository.save(byId.get());
            Optional<User> user = userRepository.findById(appointmentDTO.getStudentID());

            userServiceIMPL.mailSender(user.get().getUsername(),"Appointment Approved","Your appointment was approved on"+formattedDate);
            return new ResponseEntity(new StandardResponse(true,"Appointment approved."), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Approve appointment unsuccessful.");
        }
    }
}
