package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.ExamResultDTO;
import org.springframework.http.ResponseEntity;

public interface ExamResultService {
    ResponseEntity addExamResult(ExamResultDTO examResultDTO);

    ResponseEntity editExamResult(ExamResultDTO examResultDTO);

    ResponseEntity examResultByStudentID(int studentID);

    ResponseEntity examResultByTestID(int testID);

    ResponseEntity removeResult(int resultID);
}
