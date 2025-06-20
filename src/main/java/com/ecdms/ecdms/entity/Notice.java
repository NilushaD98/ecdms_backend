package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer noticeId;
    private String title;
    private String content;
    private Date postedDate;
    private String postedBy;

}
