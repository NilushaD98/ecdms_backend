package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_role_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id", nullable = false)
    private Integer userRoleId;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public UserRoleDetails(Role role, User user) {
        this.role = role;
        this.user = user;
    }
}