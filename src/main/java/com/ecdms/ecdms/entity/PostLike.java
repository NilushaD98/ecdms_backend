package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "post_like")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postLikeID;
    private Date likeDate;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
