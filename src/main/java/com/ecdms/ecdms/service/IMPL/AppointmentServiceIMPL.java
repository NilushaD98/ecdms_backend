package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.AppointmentDTO;
import com.ecdms.ecdms.dto.request.AppointmentsByDateDTO;
import com.ecdms.ecdms.entity.Appointment;
import com.ecdms.ecdms.entity.AppointmentTime;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.entity.User;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.AppointmentRepository;
import com.ecdms.ecdms.repository.AppointmentTimeRepository;
import com.ecdms.ecdms.repository.StudentRepository;
import com.ecdms.ecdms.repository.UserRepository;
import com.ecdms.ecdms.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceIMPL implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserServiceIMPL userServiceIMPL;
    private final UserRepository userRepository;
    private final AppointmentTimeRepository appointmentTimeRepository;
    private final StudentRepository studentRepository;

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
            Optional<Appointment> byId = appointmentRepository.findById(appointmentDTO.getAppointmentId());
            byId.get().setScheduledTime(appointmentDTO.getScheduledTime());
            byId.get().setConfirmed(true);
            appointmentRepository.save(byId.get());
            Optional<Student> student = studentRepository.findById(appointmentDTO.getStudentID());

            userServiceIMPL.mailSender(student.get().getEmail(),"Appointment Approved","Your appointment was approved on"+formattedDate);
            return new ResponseEntity(new StandardResponse(true,"Appointment approved."), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Approve appointment unsuccessful.");
        }
    }

    @Override
    public ResponseEntity getAvailableTimeAccordingToDate(AppointmentDTO appointmentDTO) {
        try {


            return null;


        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in get times.");
        }
    }

    @Override
    public ResponseEntity getPendingAppointment() {
        try {
            List<Appointment> appointmentList = appointmentRepository.getPendingAppointments();
            List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
            for(Appointment appointment:appointmentList){
                Optional<Student> byId = studentRepository.findById(appointment.getStudentID());
                AppointmentDTO appointmentDTO = new AppointmentDTO(
                        appointment.getAppointmentId(),
                        appointment.getStudentID(),
                        byId.get().getFullName(),
                        appointment.getReason(),
                        appointment.getScheduledTime()==null?new Date():appointment.getScheduledTime(),
                        appointment.isConfirmed()
                );
                log.info(appointmentDTO.toString());
                appointmentDTOList.add(appointmentDTO);
            }
            return new ResponseEntity(new StandardResponse(200,"Appointment approved.",appointmentDTOList), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred get pending appointments.");
        }
    }

    @Override
    public ResponseEntity getAppointmentDateVise() {
        try {

            List<Appointment> appointments = appointmentRepository.findAllSortedByDateAndTime();

            Map<String, List<AppointmentDTO>> groupedMap = new TreeMap<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (Appointment appt : appointments) {
                Optional<Student> studentOpt = studentRepository.findById(appt.getStudentID());
                String studentName = studentOpt.map(Student::getFullName).orElse("Unknown");

                AppointmentDTO dto = new AppointmentDTO(
                        appt.getAppointmentId(),
                        appt.getStudentID(),
                        studentName,
                        appt.getReason(),
                        appt.getScheduledTime(),
                        appt.isConfirmed()
                );

                // Get local date as string
                String dateKey = appt.getScheduledTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .format(formatter);

                groupedMap.computeIfAbsent(dateKey, k -> new ArrayList<>()).add(dto);
            }

            List<AppointmentsByDateDTO> result = new ArrayList<>();
            for (Map.Entry<String, List<AppointmentDTO>> entry : groupedMap.entrySet()) {
                try {
                    LocalDate localDate = LocalDate.parse(entry.getKey(), formatter);
                    result.add(new AppointmentsByDateDTO(localDate, entry.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return new ResponseEntity(new StandardResponse(200,"Appointment approved.",result), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred get appointments.");

        }
    }

    @Override
    public ResponseEntity getAppointmentByUser(int userID) {

        try {
            List<Appointment> appointments = appointmentRepository.findAllSortedByDateAndTimeAndUser(userID);

            Map<String, List<AppointmentDTO>> groupedMap = new TreeMap<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (Appointment appt : appointments) {
                Optional<Student> studentOpt = studentRepository.findById(appt.getStudentID());
                String studentName = studentOpt.map(Student::getFullName).orElse("Unknown");

                AppointmentDTO dto = new AppointmentDTO(
                        appt.getAppointmentId(),
                        appt.getStudentID(),
                        studentName,
                        appt.getReason(),
                        appt.getScheduledTime(),
                        appt.isConfirmed()
                );

                // Get local date as string
                String dateKey = appt.getScheduledTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .format(formatter);

                groupedMap.computeIfAbsent(dateKey, k -> new ArrayList<>()).add(dto);
            }

            List<AppointmentsByDateDTO> result = new ArrayList<>();
            for (Map.Entry<String, List<AppointmentDTO>> entry : groupedMap.entrySet()) {
                try {
                    LocalDate localDate = LocalDate.parse(entry.getKey(), formatter);
                    result.add(new AppointmentsByDateDTO(localDate, entry.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return new ResponseEntity(new StandardResponse(200,"Appointment approved.",result), HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred get appointments.");

        }
    }
}
