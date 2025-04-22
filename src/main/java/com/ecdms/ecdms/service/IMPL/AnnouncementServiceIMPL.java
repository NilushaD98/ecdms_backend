package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.AnnouncementDTO;
import com.ecdms.ecdms.dto.CommentDTO;
import com.ecdms.ecdms.dto.LikeDTO;
import com.ecdms.ecdms.dto.UserDTO;
import com.ecdms.ecdms.entity.Announcement;
import com.ecdms.ecdms.entity.PostComment;
import com.ecdms.ecdms.entity.PostLike;
import com.ecdms.ecdms.entity.User;
import com.ecdms.ecdms.repository.AnnouncementRepository;
import com.ecdms.ecdms.repository.PostCommentRepository;
import com.ecdms.ecdms.repository.PostLikeRepository;
import com.ecdms.ecdms.repository.UserRepository;
import com.ecdms.ecdms.service.AnnouncementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AnnouncementServiceIMPL implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;


    @Override
    public AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = new Announcement();
        announcement.setCaption(announcementDTO.getCaption());
        announcement.setPictureLink(announcementDTO.getPictureLink());
        announcement.setPostDate(new Date());
        announcement.setLikeCount(0);
        announcement.setCommentCount(0);
        announcement = announcementRepository.save(announcement);

        return mapToDTO(announcement);
    }
    @Override
    public AnnouncementDTO getAnnouncementById(int announcementID) {
        Announcement announcement = announcementRepository.findById(announcementID)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
        return mapToDTO(announcement);
    }

    @Override
    public List<AnnouncementDTO> getAllAnnouncements(Integer userId) {
        List<Announcement> announcements = announcementRepository.findAll();
        return announcements.stream()
                .map(announcement -> {
                    List<PostComment> byAnnouncement = postCommentRepository.findByAnnouncement(announcement);
                    List<CommentDTO> commentDTOS = byAnnouncement.stream().map(this::mapToDTO).collect(Collectors.toList());
                    AnnouncementDTO dto = mapToDTO(announcement);
                    dto.setCommentDTOList(commentDTOS);
                    Optional<PostLike> like = postLikeRepository.findByAnnouncementAndUser(announcement.getAnnouncementID(), userId);
                    dto.setLikeStatus(like.isPresent());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public AnnouncementDTO updateAnnouncement(int announcementID, AnnouncementDTO announcementDTO) {
        Announcement announcement = announcementRepository.findById(announcementID)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
        announcement.setCaption(announcementDTO.getCaption());
        announcement.setPictureLink(announcementDTO.getPictureLink());
        announcement = announcementRepository.save(announcement);
        return mapToDTO(announcement);
    }

    @Override
    public void deleteAnnouncement(int announcementID) {
        announcementRepository.deleteById(announcementID);
    }

    private AnnouncementDTO mapToDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setAnnouncementID(announcement.getAnnouncementID());
        dto.setCaption(announcement.getCaption());
        dto.setPictureLink(announcement.getPictureLink());
        dto.setPostDate(announcement.getPostDate());
        dto.setLikeCount(announcement.getLikeCount());
        dto.setCommentCount(announcement.getCommentCount());
        return dto;
    }


    @Override
    public CommentDTO addComment(int announcementID, CommentDTO commentDTO, int user) {
        Announcement announcement = announcementRepository.findById(announcementID)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        PostComment comment = new PostComment();
        comment.setComment(commentDTO.getComment());
        comment.setCommentDate(new Date());
        comment.setAnnouncement(announcement);
        comment.setUser(new User(user));

        announcement.setCommentCount(announcement.getCommentCount()+1);
        comment = postCommentRepository.save(comment);
        announcementRepository.save(announcement);
        return new CommentDTO();
    }

    @Override
    public List<CommentDTO> getCommentsByAnnouncementId(int announcementID) {
        Announcement announcement = announcementRepository.findById(announcementID)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
        List<PostComment> comments = postCommentRepository.findByAnnouncement(announcement);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private CommentDTO mapToDTO(PostComment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setPostCommentID(comment.getPostCommentID());
        dto.setComment(comment.getComment());
        dto.setCommentDate(comment.getCommentDate());
        UserDTO userDTO = new UserDTO(
                "",
                comment.getUser().getStudent().getFullNameParent() != null ? comment.getUser().getStudent().getFullNameParent():""
        );
        dto.setUserDTO(userDTO);
        return dto;
    }

    @Override
    public LikeDTO likeAnnouncement(int announcementID, int userID) {
        Announcement announcement = announcementRepository.findById(announcementID)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        PostLike like = new PostLike();
        like.setAnnouncement(announcement);
        like.setLikeDate(new Date());
        like.setUser(new User(userID));
        like = postLikeRepository.save(like);
        announcement.setLikeCount(announcement.getLikeCount()+1);
        announcementRepository.save(announcement);

        return mapToDTO(like);
    }

    @Override
    public void unlikeAnnouncement(int announcementID, int userID) {
        PostLike like = postLikeRepository.findByAnnouncementAndUser(announcementID, userID)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        postLikeRepository.delete(like);
        Announcement announcement = announcementRepository.findById(announcementID)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
        announcement.setLikeCount(announcement.getLikeCount()-1);
        announcementRepository.save(announcement);
    }

    private LikeDTO mapToDTO(PostLike like) {
        LikeDTO dto = new LikeDTO();
        dto.setLikeID(like.getPostLikeID());
        dto.setUser(like.getUser());
        return dto;
    }

}
