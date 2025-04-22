package com.ecdms.ecdms.dto;

import com.ecdms.ecdms.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private int postCommentID;
    private String comment;
    private Date commentDate;
    private UserDTO userDTO; // Reference to the user who made the comment
}