package com.aguillen.recyclingmanagement.service;

import java.util.List;

import com.aguillen.recyclingmanagement.entity.Item;

public interface ItemService {

    Item create(Item item);

    Item getById(Long id);

    Item update(Long id, Item updatedItem);

    List<Item> getAll();

    Item delete(Long id);
}
