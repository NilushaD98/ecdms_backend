package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.RoleDTO;
import com.ecdms.ecdms.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    private RoleService roleService;

    @PostMapping("/add-role")
    public RequestMapping addRole(@RequestBody RoleDTO roleDTO){
        return roleService.addRole(roleDTO);
    }
}
