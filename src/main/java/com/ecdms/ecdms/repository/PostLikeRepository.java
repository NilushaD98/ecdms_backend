package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.PostLike;
import com.ecdms.ecdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Integer> {

    @Query(value = "select pl from PostLike pl where pl.announcement.announcementID=:announcementID AND pl.user.userId=:userID")
    Optional<PostLike> findByAnnouncementAndUser(@Param("announcementID") int announcementID, @Param("userID") int userID);
}
