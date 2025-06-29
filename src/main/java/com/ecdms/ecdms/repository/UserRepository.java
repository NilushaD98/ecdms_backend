package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT u FROM User u WHERE u.username=:username")
    Optional<User> findByUsernameEquals(@Param("username") String username);

    @Modifying
    @Query(value = "delete FROM User user WHERE user.userId=:userId")
    void deleteByUserID(@Param("userId") Integer userId);
}
