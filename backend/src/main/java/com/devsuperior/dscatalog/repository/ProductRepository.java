package com.devsuperior.dscatalog.repository;

import com.devsuperior.dscatalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
