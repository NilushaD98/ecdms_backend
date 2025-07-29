package com.ecdms.ecdms.dto.response;

import com.ecdms.ecdms.dto.request.AllSpecialNoticeDTO;
import com.ecdms.ecdms.dto.request.AttendanceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDetailsDTO {

    private String fullName;
    private Date dob;
    private String programme;
    private String gender;
    private String allergies;
    private String specialNotice;
    private String parentName;
    private String relationship;
    private String email;
    private String contact1;
    private String contactTwo;

    private List<TestResultsDTO> testResultsDTOList;
    private List<AllSpecialNoticeDTO> allSpecialNoticeDTOList;
    private List<PaymentDTO> paymentDTOList;
    private List<AttendanceDTO> attendanceDTOList;
}
