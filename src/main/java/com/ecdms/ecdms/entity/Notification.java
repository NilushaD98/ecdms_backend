package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notificattion")
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer notificationID;
    private Integer userID;
    private String message;
    private Boolean readStatus;


    public Notification(Integer userID, String message, Boolean readStatus) {
        this.userID = userID;
        this.message = message;
        this.readStatus = readStatus;
    }
}
