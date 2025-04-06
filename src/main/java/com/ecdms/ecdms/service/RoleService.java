package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.RoleDTO;
import org.springframework.web.bind.annotation.RequestMapping;

public interface RoleService {
    RequestMapping addRole(RoleDTO roleDTO);
}
