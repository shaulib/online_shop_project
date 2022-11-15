package com.example.vmWare_project.repository;

import com.example.vmWare_project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByTypeIn(List<String> roles);
}
