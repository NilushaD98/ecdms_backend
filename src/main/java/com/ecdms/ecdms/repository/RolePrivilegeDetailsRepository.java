package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.RolePrivilegeDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePrivilegeDetailsRepository extends JpaRepository<RolePrivilegeDetails,Integer> {
}
