package com.example.vmWare_project.service;

import com.example.vmWare_project.entity.Contact;
import com.example.vmWare_project.entity.Owner;
import com.example.vmWare_project.model.OwnerResponse;

public interface OwnerService {

    OwnerResponse getOwnerByContact(Contact contact);
}
