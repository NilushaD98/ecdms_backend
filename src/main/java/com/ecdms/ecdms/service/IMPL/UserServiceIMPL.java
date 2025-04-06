package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.AddStudentDTO;
import com.ecdms.ecdms.entity.Role;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.entity.User;
import com.ecdms.ecdms.entity.UserRoleDetails;
import com.ecdms.ecdms.enums.Status;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.RoleRepository;
import com.ecdms.ecdms.repository.StudentRepository;
import com.ecdms.ecdms.repository.UserRepository;
import com.ecdms.ecdms.repository.UserRoleDetailsRepository;
import com.ecdms.ecdms.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            for(Student addStudentDTO:all){
                AddStudentDTO student = new AddStudentDTO(
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
               addStudentDTOList.add(student);
            }
            return new ResponseEntity(new StandardResponse(200,"All Students",addStudentDTOList),HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
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
