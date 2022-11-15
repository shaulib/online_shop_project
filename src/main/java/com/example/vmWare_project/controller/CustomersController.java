package com.example.vmWare_project.controller;

import com.example.vmWare_project.model.RegisterRequest;
import com.example.vmWare_project.model.enums.ContactType;
import com.example.vmWare_project.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomersController {
    private final ContactService contactService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody RegisterRequest registerRequest) {
        registerRequest.setContactType(ContactType.CUSTOMER);
        registerRequest.setRole("customer");
        var contact = contactService.register(registerRequest);

        return ResponseEntity.ok(String.format("register %s %s successfully",contact.getFirstName(),contact.getLastName()).replace("null", ""));
    }
    @PutMapping("/edit")
    public ResponseEntity<String> editCustomer(@RequestBody RegisterRequest registerRequest) {
        registerRequest.setContactType(ContactType.CUSTOMER);
        registerRequest.setRole("customer");
        var contact = contactService.updateContact(registerRequest);

        return ResponseEntity.ok(String.format("customer %s %s successfully edited",contact.getFirstName(),contact.getLastName()).replace("null", ""));
    }


}
