package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.StudentDTO;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.repository.StudentRepository;
import com.ecdms.ecdms.service.ParentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParentServiceIMPL implements ParentService {

    private final StudentRepository studentRepository;
    @Override
    public ResponseEntity getStudentProfile(int stuID) {
        try {
            Optional<Student> byId = studentRepository.findById(stuID);
            if(byId.isPresent()){
                Student student = byId.get();
                StudentDTO dto = new StudentDTO();
                dto.setStuID(student.getStuID());
                dto.setFirstName(student.getFirstName());
                dto.setLastName(student.getLastName());
                dto.setFullName(student.getFullName());
                dto.setDob(student.getDob());
                dto.setGender(student.getGender());
                dto.setAddress(student.getAddress());
                dto.setProgram(student.getProgram());
                dto.setAgeCategory(student.getAgeCategory());

                dto.setFullNameParent(student.getFullNameParent());
                dto.setRelationship(student.getRelationship());
                dto.setEmail(student.getEmail());
                dto.setContactOne(student.getContactOne());
                dto.setContactTwo(student.getContactTwo());

                dto.setAllergies(student.getAllergies());
                dto.setSpecialNotice(student.getSpecialNotice());

                return new ResponseEntity(new StandardResponse(200,"Student by ID",dto), HttpStatus.OK);
            }else {
                throw new UsernameNotFoundException("User not found.");
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
    public ResponseEntity updateStudentProfile(StudentDTO studentDTO) {
        try {
            // Find the existing student by ID
            Optional<Student> optionalStudent = studentRepository.findById(studentDTO.getStuID());

            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();

                // Update all relevant fields
                student.setFirstName(studentDTO.getFirstName());
                student.setLastName(studentDTO.getLastName());
                student.setFullName(studentDTO.getFullName());
                student.setDob(studentDTO.getDob());
                student.setGender(studentDTO.getGender());
                student.setAddress(studentDTO.getAddress());
                student.setProgram(studentDTO.getProgram());
                student.setAgeCategory(studentDTO.getAgeCategory());

                student.setFullNameParent(studentDTO.getFullNameParent());
                student.setRelationship(studentDTO.getRelationship());
                student.setEmail(studentDTO.getEmail());
                student.setContactOne(studentDTO.getContactOne());
                student.setContactTwo(studentDTO.getContactTwo());

                student.setAllergies(studentDTO.getAllergies());
                student.setSpecialNotice(studentDTO.getSpecialNotice());

                // Save the updated student
                studentRepository.save(student);

                return new ResponseEntity(
                        new StandardResponse(200, "Student profile updated successfully", studentDTO),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity(
                        new StandardResponse(404, "Student not found", null),
                        HttpStatus.NOT_FOUND
                );
            }

        } catch (Exception e) {
            log.error("Error updating student profile: {}", e.getMessage(), e);
            return new ResponseEntity(
                    new StandardResponse(500, "Internal server error", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
