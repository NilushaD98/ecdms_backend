package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.UserRoleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDetailsRepository extends JpaRepository<UserRoleDetails,Integer> {
    @Modifying
    @Query(value = "delete FROM UserRoleDetails us WHERE us.user.userId=:userId")
    void deleteByUser(@Param("userId") Integer userId);
}
