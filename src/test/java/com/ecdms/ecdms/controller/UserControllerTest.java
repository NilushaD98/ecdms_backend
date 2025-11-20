package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.AddStudentDTO;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.service.IMPL.UserServiceIMPL;
import com.ecdms.ecdms.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceIMPL userService;
    @Test
    void getStudentByID() throws Exception{

        when(userService.getStudentByID2(302)).thenReturn(
                new Student(302,"test student first name")
        );


    }
}
