package com.server.itemservice.service.impl;

import com.server.itemservice.converter.ItemConverter;
import com.server.itemservice.dto.ItemDTO;
import com.server.itemservice.modal.Item;
import com.server.itemservice.repository.ItemRepository;
import com.server.itemservice.service.IItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemService implements IItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemConverter itemConverter;

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for(Item item : items) {
           itemDTOS.add(itemConverter.convertToDTO(item));
        }
        return itemDTOS;
    }

    @Override
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById((long) id).orElse(null);
        if (item != null) {
            return itemConverter.convertToDTO(item);
        }
        return null;
    }


    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {
        Item item = itemConverter.convertToEntity(itemDTO);
        Item savedItem = itemRepository.save(item);
        return itemConverter.convertToDTO(savedItem);
    }

    @Override
    public ItemDTO updateItem(ItemDTO itemDTO, Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            return null;
        }
        item = itemConverter.convertToEntity(itemDTO, item);
        Item updatedItem = itemRepository.save(item);
        return itemConverter.convertToDTO(updatedItem);
    }

    @Override
    public boolean deleteItem(int id) {
        Item item = itemRepository.findById((long) id).orElse(null);
        if (item == null) {
            return false;
        }
        itemRepository.delete(item);
        return true;
    }
}
