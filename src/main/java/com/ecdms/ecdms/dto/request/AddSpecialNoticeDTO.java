package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddSpecialNoticeDTO {
    private Integer specialNoticeID;
    private String message;
    private String mediaLink;
    private List<Integer> userList;
}
