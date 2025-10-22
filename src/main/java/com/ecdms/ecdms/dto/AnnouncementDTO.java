package com.ecdms.ecdms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTO {
    private int announcementID;
    private String caption;
    private String pictureLink;
    private Date postDate;
    private boolean likeStatus;
    private int likeCount;
    private int commentCount;
    private List<Integer> classroomList= new ArrayList<>();
    List<CommentDTO> commentDTOList;
}
