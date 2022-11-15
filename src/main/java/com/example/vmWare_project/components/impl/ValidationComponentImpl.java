package com.example.vmWare_project.components.impl;

import com.example.vmWare_project.components.ValidationComponent;
import com.example.vmWare_project.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;

@Component
@RequiredArgsConstructor
public class ValidationComponentImpl implements ValidationComponent {
    private final ContactRepository contactRepository;

    @Override
    public void validateContactPhoneNumber(String phone, String email) {
        var existingPhone = contactRepository.findContactByPhone(phone);
        var existingMail = contactRepository.findContactByEmail(email);
        if (existingPhone.isPresent() || existingMail.isPresent()){
            throw new EntityExistsException("phone number is already registered in the system");
        }
    }
}
