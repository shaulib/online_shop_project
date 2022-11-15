package com.example.vmWare_project.entity;

import com.example.vmWare_project.model.enums.ContactType;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "contact")
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @NotNull
    @Column(unique = true)
    private String phone;
    private String firstName;
    private String LastName;
    private String age;
    @NotNull
    @Column(unique = true)
    private String email;
    private ContactType contactType;
    @NotNull
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contact_roles",
            joinColumns = {@JoinColumn(name = "contact_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ToString.Exclude
    private List<Role> roles;
}
