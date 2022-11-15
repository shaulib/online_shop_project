package com.example.vmWare_project.repository;

import com.example.vmWare_project.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,String> {
    List<Item> findItemByName(String name);
}
