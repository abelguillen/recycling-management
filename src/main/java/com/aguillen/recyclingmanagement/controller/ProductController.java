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

import com.aguillen.recyclingmanagement.entity.Product;
import com.aguillen.recyclingmanagement.service.ProductService;

import jakarta.validation.Valid;

/**
 * A REST controller that manages operations related to products.
 * It uses the ProductService to perform these operations.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    /**
     * Logger instance for logging events.
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
    /**
     * ProductService instance for performing product operations.
     */
	@Autowired
	private ProductService service;
	
    /**
     * Handles the HTTP POST request to create a new product.
     *
     * @param product The product to be created.
     * @return A ResponseEntity containing the created product and HTTP status.
     * @throws ResponseStatusException If there is an error during the creation process.
     */
	@PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        try {
            Product createdProduct = service.create(product);
            logger.info("Created product: {}", createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            logger.error("Error creating product: {}", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating product", e);
        }
        
    }

    /**
     * Handles the HTTP GET request to retrieve a product by its ID.
     *
     * @param id The ID of the product to be retrieved.
     * @return A ResponseEntity containing the retrieved product and HTTP status.
     * @throws ResponseStatusException If there is an error during the retrieval process.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
    	try {
            Product product = service.getById(id);
            if(product == null) {
            	logger.warn("Product not found for id: {}", id);
            	return ResponseEntity.notFound().build();
            }
            logger.info("Found product: {}", product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            logger.error("Error getting product", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting product", e);
        }
    }
    
    /**
     * Handles the HTTP PUT request to update a product.
     *
     * @param id The ID of the product to be updated.
     * @param updatedProduct The product data to be updated.
     * @return A ResponseEntity containing the updated product and HTTP status.
     * @throws ResponseStatusException If there is an error during the update process.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updatedProduct) {
    	try {
            Product productResult = service.update(id, updatedProduct);
            if (productResult == null) {
                logger.warn("Product not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Updated product: {}", productResult);
            return ResponseEntity.ok(productResult);
        } catch (Exception e) {
            logger.error("Error updating product", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating product", e);
        }
    }
    
    /**
     * Handles the HTTP GET request to retrieve all products.
     *
     * @return A ResponseEntity containing the list of all products and HTTP status.
     * @throws ResponseStatusException If there is an error during the retrieval process.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
    	try {
            List<Product> products = service.getAll();
            if(products.isEmpty()) {
            	logger.info("No products found");
            	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            logger.info("Found {} products", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error getting all products", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting all products", e);
        }
    }
    
    /**
     * Handles the HTTP DELETE request to delete a product by its ID.
     *
     * @param id The ID of the product to be deleted.
     * @return A ResponseEntity containing the deleted product and HTTP status.
     * @throws ResponseStatusException If there is an error during the deletion process.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
    	try {
            Product deletedProduct = service.delete(id);
            if (deletedProduct == null) {
                logger.warn("Product not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Deleted product: {}", deletedProduct);
            return ResponseEntity.ok(deletedProduct);
        } catch (Exception e) {
            logger.error("Error deleting product", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting product", e);
        }
    }

}
