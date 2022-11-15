package com.example.vmWare_project.model;

import com.example.vmWare_project.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerResponse {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Double balance;
    private List<Item> items;
}
