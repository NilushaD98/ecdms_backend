package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.UserRoleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDetailsRepository extends JpaRepository<UserRoleDetails,Integer> {
}
