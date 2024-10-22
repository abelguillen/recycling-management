package com.aguillen.recyclingmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.aguillen.recyclingmanagement.entity.Item;
import com.aguillen.recyclingmanagement.service.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService service;

    @PostMapping
    public ResponseEntity<Item> create(@Valid @RequestBody Item item) {
        try {
            Item createdItem = service.create(item);
            logger.info("Created item: {}", createdItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (Exception e) {
            logger.error("Error creating item: {}", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating item", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Long id) {
        try {
            Item item = service.getById(id);
            if (item == null) {
                logger.warn("Item not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Found item: {}", item);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            logger.error("Error getting item", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting item", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item updatedItem) {
        try {
            Item itemResult = service.update(id, updatedItem);
            if (itemResult == null) {
                logger.warn("Item not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Updated item: {}", itemResult);
            return ResponseEntity.ok(itemResult);
        } catch (Exception e) {
            logger.error("Error updating item", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating item", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        try {
            List<Item> items = service.getAll();
            if (items.isEmpty()) {
                logger.info("No items found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            logger.info("Found {} items", items.size());
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("Error getting all items", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting all items", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> delete(@PathVariable Long id) {
        try {
            Item deletedItem = service.delete(id);
            if (deletedItem == null) {
                logger.warn("Item not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Deleted item: {}", deletedItem);
            return ResponseEntity.ok(deletedItem);
        } catch (Exception e) {
            logger.error("Error deleting item", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting item", e);
        }
    }
}
