package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.repository.StudentRepository;
import com.ecdms.ecdms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // 1. Enable Mockito
class UserServiceIMPLTest {

    @Mock// 2. Create a mock for the dependency
    private StudentRepository studentRepository;

    @InjectMocks // 3. Inject the mock into the class under test
    private UserServiceIMPL userServiceIMPL;

    @Test
    void getStudentByID2() {

        // Arrange (Given)
        Student expectStudent  = new Student(302,"test student first name");
        // Stub the repository cal
        when(studentRepository.findById(302)).thenReturn(Optional.of(expectStudent));

        // Act (When)
        Student actualStudent = userServiceIMPL.getStudentByID2(302);

        // Assert (Then)
        assertEquals("test student first name", actualStudent.getFirstName());

        // Verify (Optional - Check interaction)
        verify(studentRepository, times(1)).findById(302);

    }
}
