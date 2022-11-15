package com.example.vmWare_project.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "role")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_type", nullable = false, unique = true)
    private String type;
}
