package com.ecdms.ecdms.dto;

import com.ecdms.ecdms.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private int likeID;
    private User user; // Reference to the user who liked the announcement
}