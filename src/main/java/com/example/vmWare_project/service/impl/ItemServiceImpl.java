package com.example.vmWare_project.service.impl;

import com.example.vmWare_project.entity.Contact;
import com.example.vmWare_project.entity.Item;
import com.example.vmWare_project.entity.Owner;
import com.example.vmWare_project.model.ItemRequest;
import com.example.vmWare_project.model.ItemResponse;
import com.example.vmWare_project.model.OwnerResponse;
import com.example.vmWare_project.repository.ItemRepository;
import com.example.vmWare_project.repository.OwnerRepository;
import com.example.vmWare_project.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final OwnerRepository ownerRepository;
    private final ObjectMapper mapper;


    @Override
    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream()
                .map(item -> mapper.convertValue(item, ItemResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addItemToInventory(ItemRequest itemRequest, Contact contact) {
        final Owner owner = ownerRepository.findOwnerByOwnerContact(contact);
        if (ObjectUtils.isEmpty(contact)){
            throw new EntityExistsException("contact isn't a owner");
        }
        itemRepository.saveAll(itemRequest.getItems().stream()
                .peek(item -> item.setOwner(owner))
                .collect(Collectors.toList()));
    }

    @Override
    public List<ItemResponse> getItemByName(String name) {
       List<Item> items = itemRepository.findItemByName(name);

       return items.stream()
               .map(c-> mapper.convertValue(c,ItemResponse.class))
               .collect(Collectors.toList());
    }

    @Override
    public Item getItemById(String itemId) {
        return itemRepository.getReferenceById(itemId);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
