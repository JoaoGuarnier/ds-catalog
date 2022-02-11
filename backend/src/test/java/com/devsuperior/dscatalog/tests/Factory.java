package com.devsuperior.dscatalog.tests;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.model.Category;
import com.devsuperior.dscatalog.model.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Factory {

    public static Product createProduct() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(createCategory());
        categoryList.add(createCategory());
        Product product = new Product(1l,"Phone", "Good Phone", 800d, "http://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"), categoryList);
        return product;
    }

    public static ProductDTO createProductDto() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory() {
        return new Category(1l,"Electronics");
    }
}
