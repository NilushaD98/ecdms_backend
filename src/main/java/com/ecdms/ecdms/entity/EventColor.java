package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "event_color")
public class EventColor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer eventColorID;
    @Column(name = "p_color")
    private String primary;
    @Column(name = "s_color")
    private String secondary;

    public EventColor(String primary, String secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }
}
