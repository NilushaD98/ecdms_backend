package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {

    private int stuID;
    private String firstName;
    private String lastName;
    private String fullName;
    private Date dob;
    private String gender;
    private String address;
    private String program;
    private String ageCategory;

    private String fullNameParent;
    private String relationship;
    private String email;
    private String contactOne;
    private String contactTwo;

    private String allergies;
    private String specialNotice;
}
