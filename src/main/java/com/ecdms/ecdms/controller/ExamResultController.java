package com.ecdms.ecdms.controller;


import com.ecdms.ecdms.dto.request.ExamResultDTO;
import com.ecdms.ecdms.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam-result")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class ExamResultController {

    private final ExamResultService examResultService;

    @PostMapping("/add-exam-result")
    public ResponseEntity addExamResult(@RequestBody ExamResultDTO examResultDTO){
        return examResultService.addExamResult(examResultDTO);
    }
    @PutMapping("/edit-exam-result")
    public ResponseEntity editExamResult(@RequestBody ExamResultDTO examResultDTO){
        return examResultService.editExamResult(examResultDTO);
    }
    @GetMapping("/exam-result-by-test-id")
    public ResponseEntity examResultByTestID(@RequestParam("testID")int testID){
        return examResultService.examResultByTestID(testID);
    }

    @DeleteMapping("/remove-result")
    public ResponseEntity removeResult(@RequestParam("resultID")int resultID){
        return examResultService.removeResult(resultID);
    }
}
