package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "special_notice_user")
public class SpecialNoticeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer special_notice_user_id;
    private Integer userID;
    private Integer specialNotice;
    private boolean readStatus;

    public SpecialNoticeUser(Integer userID, Integer specialNotice, boolean readStatus) {
        this.userID = userID;
        this.specialNotice = specialNotice;
        this.readStatus = readStatus;
    }
}
