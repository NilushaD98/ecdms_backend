package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.TestTypeDTO;
import com.ecdms.ecdms.service.TestTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-type")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class TestTypeController {
    private final TestTypeService testTypeService;

    @PostMapping("/add-test-type")
    public ResponseEntity addTestType(@RequestBody TestTypeDTO testTypeDTO){
        return testTypeService.addTestType(testTypeDTO);
    }
    @PutMapping("/update-test-type")
    public ResponseEntity updateTestType(@RequestBody TestTypeDTO testTypeDTO){
        return testTypeService.updateTestType(testTypeDTO);
    }
    @GetMapping("/get-all-test-type")
    public ResponseEntity getAllTestTypes(){
        return testTypeService.getAllTestTypes();
    }
    @GetMapping("/get-test-type-by-id")
    public ResponseEntity getTestTypeByID(@RequestParam("testTypeID") int testTypeID){
        return testTypeService.getTestTypeByID(testTypeID);
    }
    @DeleteMapping("/remove-test-type-by-id")
    public ResponseEntity removeTestTypeByID(@RequestParam("testTypeID") int testTypeID){
        return testTypeService.removeTestTypeByID(testTypeID);
    }

    @GetMapping("/get-test-type-all-details-by-id")
    public ResponseEntity getTestTypeAllDetailsByID(@RequestParam("testTypeID") int testTypeID){
        return testTypeService.getTestTypeAllDetailsByID(testTypeID);

    }
}
