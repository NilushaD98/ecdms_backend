package com.ecdms.ecdms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SocialMediaPostDTO {
    private int id;
    private UserDTO user;
    private Date time;
    private String content;
    private boolean likedStatus;
    private int likes;
    private String contentIMG;
    private int commentCount;
    private boolean commentShow;
    private List<CommentDTO> comments;
    private String newComment;
}
