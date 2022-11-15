package com.example.vmWare_project.service;

import com.example.vmWare_project.entity.Contact;
import com.example.vmWare_project.entity.Item;
import com.example.vmWare_project.model.ItemRequest;
import com.example.vmWare_project.model.ItemResponse;

import java.util.List;

public interface ItemService {
    List<ItemResponse> getAllItems();

    void addItemToInventory(ItemRequest itemRequest, Contact contact);

    List<ItemResponse> getItemByName(String name);

    Item getItemById(String itemId);

    void delete(Item item);
}
