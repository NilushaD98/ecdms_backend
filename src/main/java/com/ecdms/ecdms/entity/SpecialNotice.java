package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "special_notice")
public class SpecialNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer specialNoticeID;
    private String message;
    private String mediaLink;

    public SpecialNotice(String message, String mediaLink) {
        this.message = message;
        this.mediaLink = mediaLink;
    }
}

