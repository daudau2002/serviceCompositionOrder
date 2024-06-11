package com.server.itemservice.repository;

import com.server.itemservice.modal.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
