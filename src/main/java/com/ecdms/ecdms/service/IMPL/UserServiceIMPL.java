package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.AddStudentDTO;
import com.ecdms.ecdms.dto.request.TeacherDTO;
import com.ecdms.ecdms.entity.*;
import com.ecdms.ecdms.enums.Status;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.*;
import com.ecdms.ecdms.service.PaymentService;
import com.ecdms.ecdms.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceIMPL implements UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleDetailsRepository userRoleDetailsRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final PaymentService paymentService;
    @Override
    public ResponseEntity addStudent(AddStudentDTO addStudentDTO) {
        try {
            String samplePassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            User user = new User();
            user.setUserUuid(UUID.randomUUID().toString());
            user.setUsername(addStudentDTO.getEmail());
            user.setPassword(passwordEncoder.encode(samplePassword));
            user.setCreatedBy("admin");
            user.setUpdatedBy("admin");
            user.setStatus(Status.ACTIVE.toString());
            user.setLoginAttempts(0);
            userRepository.save(user);
            UserRoleDetails userRoleDetails = new UserRoleDetails(
                    new Role(2),
                    user
            );
            userRoleDetailsRepository.save(userRoleDetails);
            Student student = new Student(
                    addStudentDTO.getFirstName(),
                    addStudentDTO.getLastName(),
                    addStudentDTO.getFullName(),
                    addStudentDTO.getDob(),
                    addStudentDTO.getGender(),
                    addStudentDTO.getAddress(),
                    addStudentDTO.getProgram(),
                    addStudentDTO.getAgeCategory(),
                    addStudentDTO.getFullNameParent(),
                    addStudentDTO.getRelationship(),
                    addStudentDTO.getEmail(),
                    addStudentDTO.getContactOne(),
                    addStudentDTO.getContactTwo(),
                    user
            );
            studentRepository.save(student);
            boolean vortexOtp = mailSender(addStudentDTO.getEmail(), "ECDMS Password","ECDMS Password is - " + samplePassword+". Reset this when logged first time.");
            if(vortexOtp){
                if(addStudentDTO.getProgram().equals("ecd")){
                    paymentService.createYearlyPaymentPlan(student.getStuID(),"Pre School",2000);
                } else if (addStudentDTO.getProgram().equals("dc")) {
                    paymentService.createYearlyPaymentPlan(student.getStuID(),"Day Care",7000);
                }else {
                    paymentService.createYearlyPaymentPlan(student.getStuID(),"Pre School",2000);
                    paymentService.createYearlyPaymentPlan(student.getStuID(),"Day Care",7000);
                }
                return new ResponseEntity(new StandardResponse(true,"Student Added Successfully"), HttpStatus.CREATED);
            }else {
                throw new InternalServerErrorException("Email send error.");
            }
        }catch (InternalServerErrorException e){
            throw e;
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity getAllStudents() {
        try {
            List<AddStudentDTO> addStudentDTOList = new ArrayList<>();
            List<Student> all = studentRepository.findAll();
            for(Student student:all){
                AddStudentDTO addStudentDTO = new AddStudentDTO(
                        student.getStuID(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getFullName(),
                        student.getDob(),
                        student.getGender(),
                        student.getAddress(),
                        student.getProgram(),
                        student.getAgeCategory(),
                        student.getFullNameParent(),
                        student.getRelationship(),
                        student.getEmail(),
                        student.getContactOne(),
                        student.getContactTwo()
                );
               addStudentDTOList.add(addStudentDTO);
            }
            return new ResponseEntity(new StandardResponse(200,"All Students",addStudentDTOList),HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }

    }

    @Override
    public ResponseEntity getStudentByID(int userID) {

        try{
            Optional<Student> byId = studentRepository.findById(userID);
            if (byId.isPresent()){
                Student student = byId.get();
                AddStudentDTO addStudentDTO = new AddStudentDTO(
                        student.getStuID(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getFullName(),
                        student.getDob(),
                        student.getGender(),
                        student.getAddress(),
                        student.getProgram(),
                        student.getAgeCategory(),
                        student.getFullNameParent(),
                        student.getRelationship(),
                        student.getEmail(),
                        student.getContactOne(),
                        student.getContactTwo()
                );
                return new ResponseEntity(new StandardResponse(200,"Student",addStudentDTO),HttpStatus.OK);
            }else{
                throw new UsernameNotFoundException("User not present");
            }

        }catch (UsernameNotFoundException usernameNotFoundException){
            throw usernameNotFoundException;
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity deleteStudentByID(int userID) {
        try {
            Optional<Student> byId = studentRepository.findById(userID);
            if(byId.isPresent()){
                studentRepository.deleteById(userID);
                return new ResponseEntity(new StandardResponse(200,"Student Removed.",byId.get().getFirstName()),HttpStatus.OK);
            }else {
                throw new UsernameNotFoundException("Student not found in system.");
            }
        }catch (UsernameNotFoundException usernameNotFoundException){
            throw usernameNotFoundException;
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity addTeacher(TeacherDTO teacherDTO) {
        try {
            List<Classroom> classroomList = classroomRepository.findAllById(teacherDTO.getClassroomList());
            Teacher teacher = new Teacher(
                    teacherDTO.getFullName(),
                    teacherDTO.getContact(),
                    teacherDTO.getNic(),
                    teacherDTO.getDob(),
                    teacherDTO.getGender(),
                    teacherDTO.getAddress(),
                    teacherDTO.getSalary(),
                    teacherDTO.getJoiningDate(),
                    classroomList
            );
            teacherRepository.save(teacher);
            return new ResponseEntity<>(new StandardResponse(true,"Teacher added successfully."),HttpStatus.CREATED);
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity getAllTeachers() {
        List<Teacher> all = teacherRepository.findAll();
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        for(Teacher teacher:all){
            List<Classroom> classrooms = teacher.getClassrooms();
            List<Integer> classRoomIDList = new ArrayList<>();
            for(Classroom classroom:classrooms){
                classRoomIDList.add(classroom.getClassID());
            }
            TeacherDTO teacherDTO = new TeacherDTO(
                    teacher.getTeacherID(),
                    teacher.getFullName(),
                    teacher.getContact(),
                    teacher.getNic(),
                    teacher.getDob(),
                    teacher.getGender(),
                    teacher.getAddress(),
                    teacher.getSalary(),
                    teacher.getJoiningDate(),
                    classRoomIDList
            );
            teacherDTOList.add(teacherDTO);
        }
        return new ResponseEntity(new StandardResponse(200,"Teacher List",teacherDTOList),HttpStatus.OK);
    }

    @Override
    public ResponseEntity getTeacherByID(int teacherID) {
        Optional<Teacher> byId = teacherRepository.findById(teacherID);
        Teacher teacher = byId.get();
        List<Classroom> classrooms = teacher.getClassrooms();
        List<Integer> classRoomIDList = new ArrayList<>();
        for(Classroom classroom:classrooms){
            classRoomIDList.add(classroom.getClassID());
        }
        TeacherDTO teacherDTO = new TeacherDTO(
                teacher.getTeacherID(),
                teacher.getFullName(),
                teacher.getContact(),
                teacher.getNic(),
                teacher.getDob(),
                teacher.getGender(),
                teacher.getAddress(),
                teacher.getSalary(),
                teacher.getJoiningDate(),
                classRoomIDList
        );
        return new ResponseEntity(new StandardResponse(200,"Teacher By ID",teacherDTO),HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateTeacherByID(TeacherDTO teacherDTO) {
        Optional<Teacher> byId = teacherRepository.findById(teacherDTO.getTeacherID());
        Teacher teacher = byId.get();
        List<Classroom> classroomList = classroomRepository.findAllById(teacherDTO.getClassroomList());
        teacher.setFullName(teacherDTO.getFullName());
        teacher.setContact(teacherDTO.getContact());
        teacher.setNic(teacherDTO.getNic());
        teacher.setDob(teacherDTO.getDob());
        teacher.setGender(teacherDTO.getGender());
        teacher.setAddress(teacherDTO.getAddress());
        teacher.setSalary(teacherDTO.getSalary());
        teacher.setJoiningDate(teacherDTO.getJoiningDate());
        teacher.setClassrooms(classroomList);
        teacherRepository.save(teacher);
        return new ResponseEntity<>(new StandardResponse(true,"Teacher updated successfully."),HttpStatus.OK);
    }

    @Override
    public ResponseEntity removeTeacherByID(int teacherID) {
        teacherRepository.deleteById(teacherID);
        return new ResponseEntity<>(new StandardResponse(true,"Teacher removed successfully."),HttpStatus.OK);
    }

    public boolean mailSender(String toMail, String subject, String body){
        try {
            SimpleMailMessage newMail = new SimpleMailMessage();
            newMail.setTo(toMail);
            newMail.setSubject(subject);
            newMail.setText(body);
            mailSender.send(newMail);
            log.info("Mail successfully send to "+ toMail);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
}
