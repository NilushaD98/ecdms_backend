package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.ExamResultDTO;
import com.ecdms.ecdms.dto.response.ExamResultDescriptionDTO;
import com.ecdms.ecdms.entity.ExamResult;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.entity.TestType;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.ExamResultRepository;
import com.ecdms.ecdms.service.ExamResultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ExamResultServiceIMPL implements ExamResultService {

    private final ExamResultRepository examResultRepository;


    @Override
    public ResponseEntity addExamResult(ExamResultDTO examResultDTO) {
        try {
            ExamResult examResult = new ExamResult(
                    new Student(examResultDTO.getStudent()),
                    new TestType(examResultDTO.getTestType()),
                    examResultDTO.getScore(),
                    examResultDTO.getPassScore(),
                    examResultDTO.getMaxScore(),
                    examResultDTO.getScore()>examResultDTO.getPassScore()
            );
            examResultRepository.save(examResult);
            return new ResponseEntity(
                    new StandardResponse(true, "Exam result added."),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in add exam result.");
        }

    }

    @Override
    public ResponseEntity editExamResult(ExamResultDTO examResultDTO) {
        try {
            Optional<ExamResult> byId = examResultRepository.findById(examResultDTO.getResultID());
            byId.get().setStudent(new Student(examResultDTO.getStudent()));
            byId.get().setTestType(new TestType(examResultDTO.getTestType()));
            byId.get().setScore(examResultDTO.getScore());
            byId.get().setPassScore(examResultDTO.getPassScore());
            byId.get().setPassStatus(examResultDTO.getScore()>examResultDTO.getPassScore());
            examResultRepository.save(byId.get());
            return new ResponseEntity(
                    new StandardResponse(true, "Exam result edit successfully."),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in edit exam result.");
        }
    }

    @Override
    public ResponseEntity examResultByStudentID(int studentID) {
        try {
         return null;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in get exam result.");
        }
    }

    @Override
    public ResponseEntity examResultByTestID(int testID) {
        try {
            Optional<ExamResult> examResult = examResultRepository.findByTestType(testID);
            ExamResultDescriptionDTO examResultDescriptionDTO = new ExamResultDescriptionDTO(
                    examResult.get().getResultID(),
                    examResult.get().getStudent().getFullName(),
                    examResult.get().getTestType().getTestName(),
                    examResult.get().getScore(),
                    examResult.get().getMaxScore()  ,
                    examResult.get().isPassStatus()
            );
            return new ResponseEntity(
                    new StandardResponse(200, "Exam results.",examResultDescriptionDTO),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in get exam result.");
        }
    }

    @Override
    public ResponseEntity removeResult(int resultID) {

        try {
            Optional<ExamResult> byId = examResultRepository.findById(resultID);
            examResultRepository.delete(byId.get());
            return new ResponseEntity(
                    new StandardResponse(true, "Exam result deleted."),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in get exam result.");

        }
    }
}
