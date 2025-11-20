package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddStudentDTO {

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

    public AddStudentDTO(int stuID, String firstName) {
        this.stuID = stuID;
        this.firstName = firstName;
    }
}
