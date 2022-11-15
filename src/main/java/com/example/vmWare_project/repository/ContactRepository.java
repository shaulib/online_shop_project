package com.example.vmWare_project.repository;


import com.example.vmWare_project.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,String> {
    @Override
    Optional<Contact> findById(String s);

    Optional<Contact> findContactByPhone(String phone);
    Optional<Contact> findContactByEmail(String email);
}
