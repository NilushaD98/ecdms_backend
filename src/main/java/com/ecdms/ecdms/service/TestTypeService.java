package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.TestTypeDTO;
import org.springframework.http.ResponseEntity;

public interface TestTypeService {
    ResponseEntity addTestType(TestTypeDTO testTypeDTO);

    ResponseEntity updateTestType(TestTypeDTO testTypeDTO);

    ResponseEntity getAllTestTypes();

    ResponseEntity getTestTypeByID(int testTypeID);

    ResponseEntity removeTestTypeByID(int testTypeID);
}
