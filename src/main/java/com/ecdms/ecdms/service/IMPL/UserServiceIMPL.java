package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.*;
import com.ecdms.ecdms.dto.response.PaymentDTO;
import com.ecdms.ecdms.dto.response.StudentDetailsDTO;
import com.ecdms.ecdms.dto.response.TestResultsDTO;
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

import java.util.*;

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
    private final SpecialNoticeRepository specialNoticeRepository;
    private final SpecialNoticeUserRepository specialNoticeUserRepository;
    private final ExamResultRepository examResultRepository;
    private final PaymentRepository paymentRepository;
    private final AttendanceRepository attendanceRepository;
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
                    addStudentDTO.getContactTwo()
            );
            student.setStatus("ACTIVE");
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
            List<Student> all = studentRepository.findAllActive();
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
        Optional<Student> byId = studentRepository.findById(userID);
            Optional<User> byUsernameEquals = userRepository.findByUsernameEquals(byId.get().getEmail());
            studentRepository.delete(byId.get());
            userRepository.delete(byUsernameEquals.get());

            return new ResponseEntity(new StandardResponse(true,"Student Removed."),HttpStatus.OK);

    }

    @Override
    public ResponseEntity addTeacher(TeacherDTO teacherDTO) {
        try {
            String samplePassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

            User user = new User();
            user.setUserUuid(UUID.randomUUID().toString());
            user.setUsername(teacherDTO.getEmail());
            user.setPassword(passwordEncoder.encode(samplePassword));
            user.setCreatedBy("admin");
            user.setUpdatedBy("admin");
            user.setStatus(Status.ACTIVE.toString());
            user.setLoginAttempts(0);
            userRepository.save(user);
            UserRoleDetails userRoleDetails = new UserRoleDetails(
                    new Role(4),
                    user
            );
            userRoleDetailsRepository.save(userRoleDetails);
            log.info(teacherDTO.getClassroomList().toString());
            List<Classroom> classroomList = classroomRepository.findAllById(teacherDTO.getClassroomList());
            log.info(""+classroomList.size());
            Teacher teacher = new Teacher(
                    teacherDTO.getFullName(),
                    teacherDTO.getContact(),
                    teacherDTO.getNic(),
                    teacherDTO.getDob(),
                    teacherDTO.getEmail(),
                    teacherDTO.getGender(),
                    teacherDTO.getAddress(),
                    teacherDTO.getSalary(),
                    teacherDTO.getJoiningDate(),
                    classroomList
            );
            for (Classroom classroom : classroomList) {
                classroom.getTeachers().add(teacher); // Add teacher to each classroom
            }
            teacherRepository.save(teacher);
            boolean vortexOtp = mailSender(teacherDTO.getEmail(), "ECDMS Password","ECDMS Password is - " + samplePassword+". Reset this when logged first time.");
            if(vortexOtp){
                return new ResponseEntity<>(new StandardResponse(true,"Teacher added successfully."),HttpStatus.CREATED);
            }else {
                throw new InternalServerErrorException("Email send error.");
            }
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
                    teacher.getEmail(),
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
                teacher.getEmail(),
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
        try {
            Teacher existingTeacher = teacherRepository.findById(teacherDTO.getTeacherID())
                    .orElseThrow(() -> new InternalServerErrorException("Teacher not found"));

            List<Classroom> newClassroomList = classroomRepository.findAllById(teacherDTO.getClassroomList());

            Set<Classroom> oldClassroomSet = new HashSet<>(existingTeacher.getClassrooms());

            for (Classroom classroom : oldClassroomSet) {
                classroom.getTeachers().remove(existingTeacher);
            }

            existingTeacher.getClassrooms().clear();

            for (Classroom classroom : newClassroomList) {
                classroom.getTeachers().add(existingTeacher); // Update inverse side
                existingTeacher.getClassrooms().add(classroom); // Update owning side
            }

            existingTeacher.setFullName(teacherDTO.getFullName());
            existingTeacher.setContact(teacherDTO.getContact());
            existingTeacher.setNic(teacherDTO.getNic());
            existingTeacher.setDob(teacherDTO.getDob());
            existingTeacher.setGender(teacherDTO.getGender());
            existingTeacher.setAddress(teacherDTO.getAddress());
            existingTeacher.setSalary(teacherDTO.getSalary());
            existingTeacher.setJoiningDate(teacherDTO.getJoiningDate());

            classroomRepository.saveAll(newClassroomList);
            teacherRepository.save(existingTeacher);

            return new ResponseEntity<>(new StandardResponse(true, "Teacher updated successfully."), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error occurred while updating teacher.");
        }
    }

    @Override
    public ResponseEntity removeTeacherByID(int teacherID) {
        Optional<Teacher> byId = teacherRepository.findById(teacherID);
        Optional<User> byUsernameEquals = userRepository.findByUsernameEquals(byId.get().getEmail());
        userRepository.delete(byUsernameEquals.get());
        teacherRepository.deleteById(teacherID);
        return new ResponseEntity<>(new StandardResponse(true,"Teacher removed successfully."),HttpStatus.OK);
    }

    @Override
    public ResponseEntity getStudentFullDetailsByID(int userID) {

        try {
            Optional<Student> byId = studentRepository.findById(userID);
            StudentDetailsDTO studentDetailsDTO = new StudentDetailsDTO();
            studentDetailsDTO.setFullName(byId.get().getFullName());
            studentDetailsDTO.setDob(byId.get().getDob());
            studentDetailsDTO.setProgramme(
                    byId.get().getProgram().equals("ecd")?"ECD Center":
                            byId.get().getProgram().equals("dc")?"Daycare Center":
                                    byId.get().getProgram().equals("ecddc")?"ECD & Daycare Center":""
            );
            studentDetailsDTO.setGender(byId.get().getGender());
            studentDetailsDTO.setAllergies(byId.get().getAllergies());
            studentDetailsDTO.setSpecialNotice(byId.get().getSpecialNotice());
            studentDetailsDTO.setParentName(byId.get().getFullNameParent());
            studentDetailsDTO.setRelationship(byId.get().getRelationship());
            studentDetailsDTO.setEmail(byId.get().getEmail());
            studentDetailsDTO.setContact1(byId.get().getContactOne());
            studentDetailsDTO.setContactTwo(byId.get().getContactTwo());

            List<ExamResult> examResults = examResultRepository.findByStudent(userID);
            List<TestResultsDTO> testResultsDTOList = new ArrayList<>();
            for(ExamResult examResult:examResults){
                TestResultsDTO testResultsDTO = new TestResultsDTO(
                        examResult.getTestType().getTestName(),
                        examResult.getScore(),
                        examResult.isPassStatus(),
                        100.00
                );
                testResultsDTOList.add(testResultsDTO);
            }
            studentDetailsDTO.setTestResultsDTOList(testResultsDTOList);

            List<SpecialNoticeUser> specialNoticeUserList = specialNoticeUserRepository.findByUser(userID);
            List<AllSpecialNoticeDTO> allSpecialNoticeDTOList = new ArrayList<>();
            for(SpecialNoticeUser specialNoticeUser: specialNoticeUserList){
                Optional<SpecialNotice> specialNotice = specialNoticeRepository.findById(specialNoticeUser.getSpecialNotice());
                AllSpecialNoticeDTO allSpecialNoticeDTO = new AllSpecialNoticeDTO(
                        specialNotice.get().getSpecialNoticeID(),
                        specialNotice.get().getMessage(),
                        specialNotice.get().getMediaLink(),
                        null
                );
                allSpecialNoticeDTOList.add(allSpecialNoticeDTO);
            }
            studentDetailsDTO.setAllSpecialNoticeDTOList(allSpecialNoticeDTOList);

            List<Payment> paymentList = paymentRepository.findByUserID(userID);
            List<PaymentDTO> paymentDTOList = new ArrayList<>();
            for (Payment payment:paymentList) {
                PaymentDTO paymentDTO = new PaymentDTO(
                        payment.getPaymentId(),
                        payment.getType(),
                        payment.getAmount(),
                        payment.getDueDate(),
                        payment.getPaidDate(),
                        payment.isPaid()
                );
                paymentDTOList.add(paymentDTO);
            }
            studentDetailsDTO.setPaymentDTOList(paymentDTOList);


            List<Attendance> attendanceList = attendanceRepository.findByUser(userID);
            List<AttendanceDTO> attendanceDTOList = new ArrayList<>();
            for(Attendance attendance:attendanceList){
                AttendanceDTO attendanceDTO = new AttendanceDTO(
                        attendance.getAttendanceID(),
                        attendance.getDate(),
                        attendance.isPresent(),
                        attendance.getRemarks()
                );
                attendanceDTOList.add(attendanceDTO);
            }
            studentDetailsDTO.setAttendanceDTOList(attendanceDTOList);

            return new ResponseEntity<>(new StandardResponse(200,"",studentDetailsDTO),HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred.");
        }
    }

    @Override
    public ResponseEntity updateStudentByParent(UpdateStudentDTO updateStudentDTO) {
        try {
            Optional<Student> byId = studentRepository.findById(updateStudentDTO.getUserID());
            byId.get().setAllergies(updateStudentDTO.getAllergies());
            byId.get().setSpecialNotice(updateStudentDTO.getSpecialNotice());
            byId.get().setContactOne(updateStudentDTO.getContactOne());
            byId.get().setContactTwo(updateStudentDTO.getContactTwo());

            studentRepository.save(byId.get());

            return new ResponseEntity(new StandardResponse(true,"Updated."),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred.");

        }

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
