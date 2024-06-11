package com.server.itemservice.service;

import com.server.itemservice.dto.ItemDTO;

import java.util.List;

public interface IItemService {
    public List<ItemDTO> getAllItems();
    public ItemDTO getItemById(Long id);
    public ItemDTO addItem(ItemDTO itemDTO);
    public ItemDTO updateItem(ItemDTO itemDTO, Long id);
    public boolean deleteItem(int id);
}
