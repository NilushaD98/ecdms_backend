package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    @Column(name = "start_date")
    private Date start;
    @Column(name = "end_date")
    private Date end;
    private Integer color_id;

    public Event(String title, Date start, Date end, Integer color_id) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.color_id = color_id;
    }
}
