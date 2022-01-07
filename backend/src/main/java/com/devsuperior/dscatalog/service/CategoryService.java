package com.devsuperior.dscatalog.service;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.model.Category;
import com.devsuperior.dscatalog.repository.CategoryRepository;
import com.devsuperior.dscatalog.service.exception.EntityNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<CategoryDTO> categoryDTOS = categoryRepository.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList());
        return categoryDTOS;
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        Category category = optionalCategory.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        CategoryDTO categoryDTO = new CategoryDTO(category);

        return categoryDTO;
    }

}
