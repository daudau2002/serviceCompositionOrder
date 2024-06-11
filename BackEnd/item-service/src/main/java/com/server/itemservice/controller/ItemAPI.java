package com.server.itemservice.controller;

import com.server.itemservice.dto.ItemDTO;
import com.server.itemservice.service.IItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin
public class ItemAPI {

    @Autowired
    private IItemService itemService;

    @Operation(summary = "Get all items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the items"),
            @ApiResponse(responseCode = "404", description = "Items not found")
    })
    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @Operation(summary = "Get an item by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the item"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        if (itemDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemDTO);
    }

    @Operation(summary = "Add a new item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid item supplied")
    })
    @PostMapping
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO itemDTO) {
        return ResponseEntity.ok(itemService.addItem(itemDTO));
    }

    @Operation(summary = "Update an existing item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "400", description = "Invalid item supplied")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO itemDTO, @PathVariable Long id) {
        ItemDTO updatedItemDTO = itemService.updateItem(itemDTO, id);
        if (updatedItemDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedItemDTO);
    }

    @Operation(summary = "Delete an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteItem(@PathVariable int id) {
        boolean deleted = itemService.deleteItem(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deleted);
    }
}