package com.devsuperior.dscatalog.service;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.model.Category;
import com.devsuperior.dscatalog.model.Product;
import com.devsuperior.dscatalog.repository.CategoryRepository;
import com.devsuperior.dscatalog.repository.ProductRepository;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<ProductDTO> productDTOS = productRepository.findAll(pageable).map(p -> new ProductDTO(p, p.getCategories()));
        return productDTOS;
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        Product product = optionalProduct.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        ProductDTO productDTO = new ProductDTO(product, product.getCategories());

        return productDTO;
    }

//    @Transactional
//    public ProductDTO save(ProductDTO dto) {
//        Product entity = new Product(dto);
//        Product saveEntity = productRepository.save(entity);
//        return new ProductDTO(saveEntity);
//    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product entity = optionalProduct.orElseThrow(() -> new ResourceNotFoundException("Entity not found " + id));
        entity.setName(dto.getName());
        entity = productRepository.save(entity);

        return new ProductDTO(entity);


    }

    public void delete(Long id) {

        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DatabaseException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }

    }
}
