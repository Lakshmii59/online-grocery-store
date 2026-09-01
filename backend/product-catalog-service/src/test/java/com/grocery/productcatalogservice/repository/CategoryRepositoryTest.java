package com.grocery.productcatalogservice.repository;

import com.grocery.productcatalogservice.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    void saveCategory_ShouldSaveSuccessfully() {

        Category category = new Category();
        category.setCategoryName("Fruits");
        category.setDescription("Fresh Fruits");

        Category saved = categoryRepo.save(category);

        assertNotNull(saved);
        assertNotNull(saved.getCategoryId());
        assertEquals("Fruits", saved.getCategoryName());
    }

    @Test
    void findById_ShouldReturnCategory() {

        Category category = new Category();
        category.setCategoryName("Vegetables");
        category.setDescription("Fresh Vegetables");

        Category saved = categoryRepo.save(category);

        Optional<Category> result =
                categoryRepo.findById(saved.getCategoryId());

        assertTrue(result.isPresent());
        assertEquals("Vegetables", result.get().getCategoryName());
    }

    @Test
    void findAll_ShouldReturnCategories() {

        categoryRepo.save(new Category(null, "Fruits", "Fresh Fruits"));
        categoryRepo.save(new Category(null, "Snacks", "Packed Snacks"));

        assertTrue(categoryRepo.findAll().size() >= 2);
    }

    @Test
    void deleteCategory_ShouldDeleteSuccessfully() {

        Category category = new Category();
        category.setCategoryName("Bakery");
        category.setDescription("Bakery Items");

        Category saved = categoryRepo.save(category);

        categoryRepo.deleteById(saved.getCategoryId());

        assertFalse(categoryRepo.findById(saved.getCategoryId()).isPresent());
    }
}
