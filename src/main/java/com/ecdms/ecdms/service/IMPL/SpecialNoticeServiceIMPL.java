package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.AddSpecialNoticeDTO;
import com.ecdms.ecdms.dto.request.AllSpecialNoticeDTO;
import com.ecdms.ecdms.dto.request.NoticesRequestDTO;
import com.ecdms.ecdms.entity.SpecialNotice;
import com.ecdms.ecdms.entity.SpecialNoticeUser;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.SpecialNoticeRepository;
import com.ecdms.ecdms.repository.SpecialNoticeUserRepository;
import com.ecdms.ecdms.repository.StudentRepository;
import com.ecdms.ecdms.service.SpecialNoticeService;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SpecialNoticeServiceIMPL implements SpecialNoticeService {


    private final SpecialNoticeRepository specialNoticeRepository;
    private final SpecialNoticeUserRepository specialNoticeUserRepository;
    private final StudentRepository studentRepository;
    private final JavaMailSender mailSender;
    @Override
    public ResponseEntity addSpecialNotice(AddSpecialNoticeDTO addSpecialNoticeDTO) {
        try {
            SpecialNotice specialNotice = new SpecialNotice(
                    addSpecialNoticeDTO.getMessage(),
                    addSpecialNoticeDTO.getMediaLink()
            );
            specialNoticeRepository.save(specialNotice);
            List<SpecialNoticeUser> specialNoticeUserList = new ArrayList<>();
            for(int user:addSpecialNoticeDTO.getUserList()){
                SpecialNoticeUser specialNoticeUser = new SpecialNoticeUser(
                        user,
                        specialNotice.getSpecialNoticeID(),
                        false
                );
                specialNoticeUserList.add(specialNoticeUser);
            }
            specialNoticeUserRepository.saveAll(specialNoticeUserList);

            List<Student> allById = studentRepository.findAllById(addSpecialNoticeDTO.getUserList());
            for (Student student : allById) {
                boolean success = mailSender(
                        student.getEmail(),
                        "📢 Special Announcement",
                        "<p><strong>" + addSpecialNoticeDTO.getMessage() + "</strong></p>",
                        addSpecialNoticeDTO.getMediaLink(),
                        "notice-media"
                );
                if (success) {
                    log.info("Sent notice to: " + student.getEmail());
                }
            }
            return new ResponseEntity(new StandardResponse(true,"Notice added"), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in add special notice.");
        }
    }
    private byte[] downloadFile(String fileUrl) throws IOException {
        try (InputStream in = new URL(fileUrl).openStream()) {
            return in.readAllBytes();
        }
    }
    public boolean mailSender(String toMail, String subject, String body, String attachmentLink, String attachmentName) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart

            helper.setTo(toMail);
            helper.setSubject(subject);
            helper.setFrom("your-email@gmail.com"); // Set your sender email

            // Build HTML body with inline image if mediaLink exists
            String htmlBody = buildHtmlBodyWithImage(body, attachmentLink);
            helper.setText(htmlBody, true); // true = HTML

            // If mediaLink exists, embed image as inline
            if (attachmentLink != null && !attachmentLink.trim().isEmpty()) {
                String contentId = "image1"; // Used in <img src="cid:image1">
                InputStreamSource imageResource = new ByteArrayResource(downloadFile(attachmentLink));
                helper.addInline(contentId, imageResource, "image/jpeg"); // Adjust MIME type if needed
            }

            mailSender.send(mimeMessage);
            log.info("Email with inline image sent to: " + toMail);
            return true;

        } catch (Exception e) {
            log.error("Failed to send email to: " + toMail, e);
            return false;
        }
    }
    private String buildHtmlBodyWithImage(String message, String mediaLink) {
        StringBuilder html = new StringBuilder();
        html.append("<div style='font-family: Arial, sans-serif; padding: 20px; color: #333;'>");
        html.append("  <h2 style='color: #2c3e50;'>📢 Special Notice</h2>");
        html.append("  <p style='font-size: 16px; line-height: 1.6;'>").append(message).append("</p>");

        if (mediaLink != null && !mediaLink.trim().isEmpty()) {
            html.append("  <div style='margin: 20px 0;'>");
            html.append("    <img src='cid:image1' alt='Notice Image' style='max-width: 100%; height: auto; border-radius: 8px;' />");
            html.append("  </div>");
        }

        html.append("  <hr>");
        html.append("  <p><em>From: ECDMS Team</em></p>");
        html.append("</div>");

        return html.toString();
    }

    @Override
    public ResponseEntity removeSpecialNotice(int noteID) {
        try {
            Optional<SpecialNotice> byId = specialNoticeRepository.findById(noteID);
            specialNoticeRepository.delete(byId.get());
            specialNoticeUserRepository.removeSpecialNoticeUserByNoticeID(noteID);
            return new ResponseEntity<>(new StandardResponse(true,"Special Notice removed."),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in remove special notice.");
        }
    }

    @Override
    public ResponseEntity getAllSpecialNotices() {
        try {
            List<SpecialNotice> all = specialNoticeRepository.findAll();
            List<AllSpecialNoticeDTO> allSpecialNoticeDTOList = new ArrayList<>();
            for (SpecialNotice specialNotice:all){
                List<Integer> userList = specialNoticeUserRepository.findUsers(specialNotice.getSpecialNoticeID());
                AllSpecialNoticeDTO allSpecialNoticeDTO = new AllSpecialNoticeDTO(
                        specialNotice.getSpecialNoticeID(),
                        specialNotice.getMessage(),
                        specialNotice.getMediaLink(),
                        userList
                );
                allSpecialNoticeDTOList.add(allSpecialNoticeDTO);
            }
            return new ResponseEntity(new StandardResponse(200,"All Special Notices",allSpecialNoticeDTOList),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in remove special notice.");
        }
    }

    @Override
    public ResponseEntity getSpecialNoticeUserVise(NoticesRequestDTO noticesRequestDTO) {
        try {
            List<SpecialNoticeUser> specialNoticeUserList = specialNoticeUserRepository.findByUserAndReadStatus(noticesRequestDTO.getUser(),noticesRequestDTO.isReadStatus());
            List<AllSpecialNoticeDTO> allSpecialNoticeDTOList = new ArrayList<>();
            for(SpecialNoticeUser specialNoticeUser:specialNoticeUserList){
                Optional<SpecialNotice> byId = specialNoticeRepository.findById(specialNoticeUser.getSpecialNotice());
                AllSpecialNoticeDTO allSpecialNoticeDTO = new AllSpecialNoticeDTO(
                        byId.get().getSpecialNoticeID(),
                        byId.get().getMessage(),
                        byId.get().getMediaLink(),
                        null
                );
                allSpecialNoticeDTOList.add(allSpecialNoticeDTO);
            }
            return new ResponseEntity(new StandardResponse(200,"All Special Notices",allSpecialNoticeDTOList),HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in get special notice user vise.");
        }
    }
}
