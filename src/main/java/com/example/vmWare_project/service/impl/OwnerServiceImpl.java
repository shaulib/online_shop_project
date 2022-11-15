package com.example.vmWare_project.service.impl;

import com.example.vmWare_project.entity.Contact;
import com.example.vmWare_project.entity.Owner;
import com.example.vmWare_project.model.OwnerResponse;
import com.example.vmWare_project.repository.ContactRepository;
import com.example.vmWare_project.repository.OwnerRepository;
import com.example.vmWare_project.service.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final ContactRepository contactRepository;
    private final ObjectMapper mapper;


    @Override
    @SneakyThrows
    public OwnerResponse getOwnerByContact(Contact contact) {
        if (ObjectUtils.isEmpty(contact)){
            throw new EntityExistsException("owner wasn't found");
        }

        return Optional.ofNullable(contact.getOwner())
                .map(o->OwnerResponse.builder()
                        .firstName(contact.getFirstName())
                        .lastName(contact.getLastName())
                        .phone(contact.getPhone())
                        .email(contact.getEmail())
                        .balance(o.getBalance())
                        .items(o.getItems())
                        .build())
                .orElseThrow(()-> {
                    throw new EntityExistsException("can't find owner");
                });
    }
}
