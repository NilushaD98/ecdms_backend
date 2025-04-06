package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.request.RoleDTO;
import com.ecdms.ecdms.entity.Role;
import com.ecdms.ecdms.repository.RoleRepository;
import com.ecdms.ecdms.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceIMPL implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public RequestMapping addRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        role.setRoleCode(role.getRoleCode());
        role.setStatus(roleDTO.getStatus());

        return null;
    }
}
