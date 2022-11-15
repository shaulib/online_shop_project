package com.example.vmWare_project.controller;

import com.example.vmWare_project.model.OwnerResponse;
import com.example.vmWare_project.model.RegisterRequest;
import com.example.vmWare_project.model.enums.ContactType;
import com.example.vmWare_project.service.ContactService;
import com.example.vmWare_project.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/owners")
@RequiredArgsConstructor
@Slf4j
public class OwnersController {

    private final OwnerService ownerService;
    private final ContactService contactService;


    @PostMapping("/register")
    public ResponseEntity<String> registerOwner(@RequestBody RegisterRequest registerRequest) {
        registerRequest.setContactType(ContactType.OWNER);
        registerRequest.setRole("owner");
        var contact = contactService.register(registerRequest);

        return ResponseEntity.ok(String.format("register %s %s successfully",contact.getFirstName(),contact.getLastName()).replace("null", ""));
    }

    @PostMapping("/get-owner-info")
    public ResponseEntity<OwnerResponse> getOwnerInfo(@RequestParam String phone, @RequestParam String email) {
        var contact = contactService.getContactByMailOrPhone(phone,email);

        return ResponseEntity.ok(ownerService.getOwnerByContact(contact));
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editOwner(@RequestBody RegisterRequest registerRequest) {
        registerRequest.setContactType(ContactType.OWNER);
        registerRequest.setRole("owner");
        var contact = contactService.updateContact(registerRequest);

        return ResponseEntity.ok(String.format("owner %s %s successfully edited",contact.getFirstName(),contact.getLastName()).replace("null", ""));
    }

}
