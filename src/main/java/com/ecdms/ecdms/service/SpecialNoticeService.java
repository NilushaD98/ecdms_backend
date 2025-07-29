package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.AddSpecialNoticeDTO;
import com.ecdms.ecdms.dto.request.NoticesRequestDTO;
import org.springframework.http.ResponseEntity;

public interface SpecialNoticeService {
    ResponseEntity addSpecialNotice(AddSpecialNoticeDTO addSpecialNoticeDTO);

    ResponseEntity removeSpecialNotice(int noteID);

    ResponseEntity getAllSpecialNotices();

    ResponseEntity getSpecialNoticeUserVise(NoticesRequestDTO noticesRequestDTO);
}
