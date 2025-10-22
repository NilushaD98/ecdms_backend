package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.AnnouncementDTO;
import com.ecdms.ecdms.dto.CommentDTO;
import com.ecdms.ecdms.dto.LikeDTO;
import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.entity.User;
import com.ecdms.ecdms.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping("/add-announcement")
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        AnnouncementDTO createdAnnouncement = announcementService.createAnnouncement(announcementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
    }

    @GetMapping("/get-all-announcements")
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String userType) {
        List<AnnouncementDTO> announcements = announcementService.getAllAnnouncements(user.getUserId(), userType);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/get-announcement-by-id")
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@RequestParam int announcementID) {
        AnnouncementDTO announcement = announcementService.getAnnouncementById(announcementID);
        return ResponseEntity.ok(announcement);
    }

    @PutMapping("/updateAnnouncement")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @PathVariable int announcementID,
            @RequestBody AnnouncementDTO announcementDTO) {
        AnnouncementDTO updatedAnnouncement = announcementService.updateAnnouncement(announcementID, announcementDTO);
        return ResponseEntity.ok(updatedAnnouncement);
    }

    @DeleteMapping("/deleteAnnouncement")
    public ResponseEntity<Void> deleteAnnouncement(@RequestParam int announcementID) {
        announcementService.deleteAnnouncement(announcementID);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-comment")
    public ResponseEntity addComment(
            @RequestParam int announcementID,
            @RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal User user) {
        CommentDTO createdComment = announcementService.addComment(announcementID, commentDTO, user.getUserId()     );
        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponse(true,"comment added."));
    }

    @GetMapping("/get-comments-by-announcement-id")
    public ResponseEntity<List<CommentDTO>> getCommentsByAnnouncementId(@RequestParam int announcementID) {
        List<CommentDTO> comments = announcementService.getCommentsByAnnouncementId(announcementID);
        return ResponseEntity.ok(comments);}

    @PostMapping("/like-announcement")
    public ResponseEntity likeAnnouncement(
            @RequestParam int announcementID,
            @AuthenticationPrincipal User user) {
        LikeDTO like = announcementService.likeAnnouncement(announcementID, user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(new StandardResponse(true,"liked"));
    }

    @DeleteMapping("/unlike-announcement")
    public ResponseEntity unlikeAnnouncement(
            @RequestParam int announcementID,
            @AuthenticationPrincipal User user) {
        announcementService.unlikeAnnouncement(announcementID, user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(new StandardResponse(true,"unliked"));
    }
}
