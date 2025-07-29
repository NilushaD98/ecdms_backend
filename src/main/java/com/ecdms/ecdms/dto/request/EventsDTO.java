package com.ecdms.ecdms.dto.request;

import com.ecdms.ecdms.dto.response.EventColorDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventsDTO {
    private Integer id;
    private String title;
    private Date start;
    private Date end;
    private EventColorDTO color;
}
