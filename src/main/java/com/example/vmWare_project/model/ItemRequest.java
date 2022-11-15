package com.example.vmWare_project.model;

import com.example.vmWare_project.entity.Item;
import lombok.Data;

import java.util.List;

@Data
public class ItemRequest {
    private List<Item> items;
    private String ownerMail;
    private String phone;
}
