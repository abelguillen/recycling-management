package com.aguillen.recyclingmanagement.service;

import java.util.List;

import com.aguillen.recyclingmanagement.entity.Product;

public interface ProductService {

    /**
     * Creates a new product in the database.
     *
     * @param product The product to be created.
     * @return The created product.
     */
    Product create(Product product);

    /**
     * Retrieves a product by its ID from the database.
     *
     * @param id The ID of the product to be retrieved.
     * @return The retrieved product, or null if no product was found with the given ID.
     */
    Product getById(Long id);

    /**
     * Updates a product in the database.
     *
     * @param id The ID of the product to be updated.
     * @param updatedProduct The product data to be updated.
     * @return The updated product, or null if no product was found with the given ID.
     */
    Product update(Long id, Product updatedProduct);

    /**
     * Retrieves all products from the database.
     *
     * @return A list of all products.
     */
    List<Product> getAll();

    /**
     * Deletes a product by its ID from the database.
     *
     * @param id The ID of the product to be deleted.
     * @return The deleted product, or null if no product was found with the given ID.
     */
    Product delete(Long id);
}
