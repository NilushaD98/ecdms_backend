package com.ecdms.ecdms.dto.request;

import com.ecdms.ecdms.dto.response.EventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalendarDTO {

    private EventsDTO[] events;
}
