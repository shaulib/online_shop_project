package com.example.vmWare_project.repository;

import com.example.vmWare_project.entity.Contact;
import com.example.vmWare_project.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,String> {

    Owner findOwnerByOwnerContact(Contact contact);
}
