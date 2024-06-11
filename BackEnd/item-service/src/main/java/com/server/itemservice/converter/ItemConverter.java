package com.server.itemservice.converter;

import com.server.itemservice.dto.ItemDTO;
import com.server.itemservice.modal.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemConverter {
    public ItemDTO convertToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setImage(item.getImage());
        itemDTO.setCategory(item.getCategory());
        return itemDTO;
    }

    public Item convertToEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setQuantity(itemDTO.getQuantity());
        item.setPrice(itemDTO.getPrice());
        item.setDescription(itemDTO.getDescription());
        item.setImage(itemDTO.getImage());
        item.setCategory(itemDTO.getCategory());
        return item;
    }

    public Item convertToEntity(ItemDTO itemDTO, Item item) {
        item.setName(itemDTO.getName());
        item.setQuantity(itemDTO.getQuantity());
        item.setPrice(itemDTO.getPrice());
        item.setDescription(itemDTO.getDescription());
        item.setImage(itemDTO.getImage());
        item.setCategory(itemDTO.getCategory());
        return item;
    }

}
