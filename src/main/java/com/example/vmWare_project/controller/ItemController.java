package com.example.vmWare_project.controller;

import com.example.vmWare_project.entity.Contact;
import com.example.vmWare_project.entity.Item;
import com.example.vmWare_project.model.*;
import com.example.vmWare_project.service.ContactService;
import com.example.vmWare_project.service.ItemService;
import com.example.vmWare_project.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path ="/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;
    private final OwnerService ownerService;
    private final ContactService contactService;

    @GetMapping("/get-all-Inventory")
    public ResponseEntity<List<ItemResponse>> getAllItems() {

        return ResponseEntity.ok(itemService.getAllItems());
    }

    @PostMapping("/add-item-for-sale")
    public ResponseEntity<String> addItem(@RequestBody ItemRequest itemRequest) {
        var contact = contactService.getContactByMailOrPhone(itemRequest.getPhone(),itemRequest.getOwnerMail());
        if (ObjectUtils.isEmpty(contact)){
            throw new EntityExistsException("contact isn't registered");
        }

        itemService.addItemToInventory(itemRequest,contact);
        return ResponseEntity.ok("Item was successfully added");
    }

    @GetMapping("/find-item-by-name")
    public ResponseEntity<List<ItemResponse>> findItemsByName(@RequestParam String name){
           return ResponseEntity.ok(itemService.getItemByName(name));
    }

    @PostMapping("/buy-item")
    public ResponseEntity<Receipt> buyItemById(PurchaseRequest purchaseRequest){
        var contact = contactService.getContactByMailOrPhone(purchaseRequest.getCustomerPhone(), purchaseRequest.getCustomerEmail());
        if (ObjectUtils.isEmpty(contact)){
            throw new EntityExistsException("customer isn't registered");
        }
        var item = itemService.getItemById(purchaseRequest.getItemId());
        if (ObjectUtils.isEmpty(item)){
            throw new EntityExistsException("item wasn't found");
        }
        if (purchaseRequest.getAmountToCharge() < item.getPrice()){
            throw new EntityExistsException("not enough amount to charge");
        }
        var receipt = createReceipt(purchaseRequest, contact, item);
        var owner = item.getOwner();
        if (!ObjectUtils.isEmpty(owner)){
            owner.setBalance(Optional.ofNullable(owner.getBalance())
                    .map(balance->balance+item.getPrice())
                    .orElse(item.getPrice()));
        }
        itemService.delete(item);

        return ResponseEntity.ok(receipt);
    }

    private Receipt createReceipt(PurchaseRequest purchaseRequest, Contact contact, Item item) {
        return Receipt.builder()
                .itemId(item.getId())
                .itemName(item.getName())
                .itemDescription(item.getDescription())
                .customerName(String.format("%s %s", contact.getFirstName(), contact.getLastName()).replace("null", ""))
                .chargedAmount(item.getPrice())
                .customerCreditCard(purchaseRequest.getCreditCardNumber())
                .build();
    }

}
