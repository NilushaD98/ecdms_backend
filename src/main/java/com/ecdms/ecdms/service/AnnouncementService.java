package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.AnnouncementDTO;
import com.ecdms.ecdms.dto.CommentDTO;
import com.ecdms.ecdms.dto.LikeDTO;
import java.util.List;
public interface AnnouncementService {
    AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO);
    AnnouncementDTO getAnnouncementById(int announcementID);

    List<AnnouncementDTO> getAllAnnouncements(Integer userId);
    AnnouncementDTO updateAnnouncement(int announcementID, AnnouncementDTO announcementDTO);
    void deleteAnnouncement(int announcementID);

    CommentDTO addComment(int announcementID, CommentDTO commentDTO, int user);

    List<CommentDTO> getCommentsByAnnouncementId(int announcementID);
    LikeDTO likeAnnouncement(int announcementID, int userID);

    void unlikeAnnouncement(int announcementID, int userID);
}
