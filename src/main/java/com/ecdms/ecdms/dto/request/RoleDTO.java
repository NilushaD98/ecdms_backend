package com.ecdms.ecdms.dto.request;

import com.ecdms.ecdms.entity.RolePrivilegeDetails;
import com.ecdms.ecdms.entity.UserRoleDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDTO {

    private Integer roleId;
    private String roleName;
    private String roleCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private String createdBy;
    private String updatedBy;
}
