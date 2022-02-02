package com.devsuperior.dscatalog.service;

import com.devsuperior.dscatalog.repository.ProductRepository;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long dependingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1;
        nonExistingId = 999;
        dependingId = 4;

        // configurando o comportamento do repository mockado
        Mockito.doNothing().when(productRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependingId);

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
