package com.ecdms.ecdms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String profilePhotoUrl; // URL to the user's profile picture
    private String name;
}
