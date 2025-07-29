package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.SpecialNoticeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialNoticeUserRepository extends JpaRepository<SpecialNoticeUser,Integer> {

    @Modifying
    @Query(value = "delete SpecialNoticeUser s where s.specialNotice=:noteID")
    void removeSpecialNoticeUserByNoticeID(int noteID);

    @Query(value = "SELECT snu FROM SpecialNoticeUser snu WHERE snu.userID=:user AND snu.readStatus=:readStatus")
    List<SpecialNoticeUser> findByUserAndReadStatus(@Param("user") Integer user,@Param("readStatus") boolean readStatus);

    @Query(value = "SELECT snu FROM SpecialNoticeUser snu WHERE snu.userID=:user")
    List<SpecialNoticeUser> findByUser(@Param("user") Integer user);

    @Query("SELECT s.userID FROM SpecialNoticeUser s WHERE s.specialNotice = :noticeId")
    List<Integer> findUsers(@Param("noticeId") Integer noticeId);
}
