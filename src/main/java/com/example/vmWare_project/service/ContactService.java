package com.example.vmWare_project.service;

import com.example.vmWare_project.entity.Contact;
import com.example.vmWare_project.model.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface ContactService extends UserDetailsService {
    Contact register(RegisterRequest registerRequest);

    Contact getContactByMailOrPhone(String phone, String email);

    Contact updateContact(RegisterRequest registerRequest);
}
