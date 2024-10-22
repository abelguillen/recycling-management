package com.aguillen.recyclingmanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aguillen.recyclingmanagement.entity.Item;
import com.aguillen.recyclingmanagement.repository.ItemRepository;
import com.aguillen.recyclingmanagement.service.ItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository repository;

    @Override
    public Item create(Item item) {
        try {
            return repository.save(item);
        } catch (DataAccessException e) {
            logger.error("Error creating item", e);
            throw new RuntimeException("Error creating item", e);
        }
    }

    @Override
    public Item getById(Long id) {
        try {
            return repository.findById(id).orElse(null);
        } catch (DataAccessException e) {
            logger.error("Error retrieving item", e);
            throw new RuntimeException("Error retrieving item", e);
        }
    }

    @Override
    public Item update(Long id, Item updatedItem) {
        try {
            Optional<Item> existingItem = repository.findById(id);
            if (existingItem.isPresent()) {
                Item item = existingItem.get();
                item.setCode(updatedItem.getCode());
                item.setName(updatedItem.getName());
                item.setDescription(updatedItem.getDescription());
                item.setPricePerKG(updatedItem.getPricePerKG());
                return repository.save(item);
            }
            return null;
        } catch (DataAccessException e) {
            logger.error("Error updating item", e);
            throw new RuntimeException("Error updating item", e);
        }
    }

    @Override
    public List<Item> getAll() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            logger.error("Error retrieving all items", e);
            throw new RuntimeException("Error retrieving all items", e);
        }
    }

    @Override
    public Item delete(Long id) {
        try {
            Optional<Item> existingItem = repository.findById(id);
            if (existingItem.isPresent()) {
                repository.deleteById(id);
                return existingItem.get();
            } else {
                return null;
            }
        } catch (DataAccessException e) {
            logger.error("Error deleting item", e);
            throw new RuntimeException("Error deleting item", e);
        }
    }
}
