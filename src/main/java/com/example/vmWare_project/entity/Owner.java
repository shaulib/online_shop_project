package com.example.vmWare_project.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "owner_id")
    private String id;
    @OneToOne(mappedBy = "owner",cascade = CascadeType.ALL)
    private Contact ownerContact;
    @OneToMany
    @JoinTable(name = "owner_items",
    joinColumns = {@JoinColumn(name = "owner_id")},
    inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private List<Item> items;
    private Double balance;
}
