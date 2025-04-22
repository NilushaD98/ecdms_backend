package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Announcement;
import com.ecdms.ecdms.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment,Integer> {
    List<PostComment> findByAnnouncement(Announcement announcement);
}
