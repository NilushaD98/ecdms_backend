package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.TestTypeDTO;
import com.ecdms.ecdms.entity.TestType;
import com.ecdms.ecdms.repository.TestTypeRepository;
import com.ecdms.ecdms.service.TestTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TestTypeServiceIMPL implements TestTypeService {

    private final TestTypeRepository testTypeRepository;

    @Override
    public ResponseEntity addTestType(TestTypeDTO testTypeDTO) {
        TestType testType = new TestType(
                testTypeDTO.getTestName(),
                testTypeDTO.getDescription()
        );
        testTypeRepository.save(testType);
        return new ResponseEntity(new StandardResponse(true,"Test Type Added."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateTestType(TestTypeDTO testTypeDTO) {
        Optional<TestType> byId = testTypeRepository.findById(testTypeDTO.getTestTypeID());
        byId.get().setTestName(testTypeDTO.getTestName());
        byId.get().setDescription(testTypeDTO.getDescription());
        testTypeRepository.save(byId.get());
        return new ResponseEntity(new StandardResponse(true,"Test Type Updated."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAllTestTypes() {
        List<TestType> all = testTypeRepository.findAll();
        List<TestTypeDTO> testTypeDTOList = new ArrayList<>();
        for(TestType testType:all){
            TestTypeDTO testTypeDTO = new TestTypeDTO(
                    testType.getTestTypeID(),
                    testType.getTestName(),
                    testType.getDescription()
            );
            testTypeDTOList.add(testTypeDTO);
        }
        return new ResponseEntity(new StandardResponse(200,"All Test Types.",testTypeDTOList), HttpStatus.OK);

    }

    @Override
    public ResponseEntity getTestTypeByID(int testTypeID) {
        Optional<TestType> byId = testTypeRepository.findById(testTypeID);
        TestTypeDTO testTypeDTO = new TestTypeDTO(
                byId.get().getTestTypeID(),
                byId.get().getTestName(),
                byId.get().getDescription()
        );
        return new ResponseEntity(new StandardResponse(200,"Test Type by ID.",testTypeDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity removeTestTypeByID(int testTypeID) {
        testTypeRepository.deleteById(testTypeID);
        return new ResponseEntity(new StandardResponse(true,"Test type removed."), HttpStatus.OK);
    }
}
