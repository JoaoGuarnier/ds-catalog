package com.devsuperior.dscatalog.service;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.model.Category;
import com.devsuperior.dscatalog.model.Product;
import com.devsuperior.dscatalog.repository.CategoryRepository;
import com.devsuperior.dscatalog.repository.ProductRepository;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;


    private long existingId;
    private long nonExistingId;
    private long dependingId;
    private PageImpl<Product> page;
    private Product product;
    private ProductDTO productDto;
    private Category category;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1;
        nonExistingId = 999;
        dependingId = 4;
        product = Factory.createProduct();
        productDto = Factory.createProductDto();
        page = new PageImpl<>(List.of(product));
        category = Factory.createCategory();


        // configurando o comportamento do repository mockado

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(existingId);
        Mockito.doReturn(Optional.empty()).when(productRepository).findById(nonExistingId);

        Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.doNothing().when(productRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependingId);

        Mockito.doReturn(product).when(productRepository).getById(existingId);
        Mockito.doThrow(EntityNotFoundException.class).when(productRepository).getById(nonExistingId);

        Mockito.doReturn(category).when(categoryRepository).getById(existingId);
        Mockito.doThrow(EntityNotFoundException.class).when(categoryRepository).getById(nonExistingId);

    }

    @Test
    public void updateShouldReturnDtoWhenIdExists() {
        ProductDTO result = productService.update(productDto, existingId);
        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           productService.update(productDto, nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnDtoWhenIdExists() {
        ProductDTO productDTO = productService.findById(existingId);
        Assertions.assertNotNull(productDTO);
        Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(nonExistingId);
        });
        Mockito.verify(productRepository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {

        Pageable pageable = PageRequest.of(0,10);
        Page<ProductDTO> result = productService.findAll(pageable);
        Assertions.assertNotNull(result);
        Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);

    }

    @Test
    public void saveShouldReturnDto() {
        ProductDTO productDto = Factory.createProductDto();
        ProductDTO productDtoReturn = productService.save(productDto);
        Assertions.assertNotNull(productDtoReturn);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExist() {
        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });

        // Verificando se o mock foi chamado e quantas vezes
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);

        //Verifica se o mock nunca foi chamado
        //Mockito.verify(productRepository, Mockito.never()).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistingId);

    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            productService.delete(dependingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependingId);

    }
}
