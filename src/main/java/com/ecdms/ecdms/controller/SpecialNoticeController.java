package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.AddSpecialNoticeDTO;
import com.ecdms.ecdms.dto.request.NoticesRequestDTO;
import com.ecdms.ecdms.service.SpecialNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/special_notice")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class SpecialNoticeController {

    private final SpecialNoticeService specialNoticeService;
    @PostMapping("/add_special_notice")
    public ResponseEntity addSpecialNotice(@RequestBody AddSpecialNoticeDTO addSpecialNoticeDTO){

        return specialNoticeService.addSpecialNotice(addSpecialNoticeDTO);
    }

    @DeleteMapping("/remove_special-notice")
    public ResponseEntity removeSpecialNotice(@RequestParam("noteID") int noteID){
        return specialNoticeService.removeSpecialNotice(noteID);
    }
    @GetMapping("/get_all_special_notices")
    public ResponseEntity getAllSpecialNotice(){
        return specialNoticeService.getAllSpecialNotices();
    }

    @PostMapping("/get_special_notice_user_vise")
    public ResponseEntity getSpecialNoticeUserVise(@RequestBody NoticesRequestDTO noticesRequestDTO){
        return specialNoticeService.getSpecialNoticeUserVise(noticesRequestDTO);
    }
}
