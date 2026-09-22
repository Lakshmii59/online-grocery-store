package com.grocery.productcatalogservice.service.impl;

import com.grocery.commonlibrary.exception.CategoryAlreadyExistsException;
import com.grocery.commonlibrary.exception.CategoryNotFoundException;
import com.grocery.commonlibrary.exception.ProductAlreadyExistsException;
import com.grocery.commonlibrary.exception.ProductNotFoundException;
import com.grocery.productcatalogservice.constants.ProductConstants;
import com.grocery.productcatalogservice.dto.*;
import com.grocery.productcatalogservice.entity.Category;
import com.grocery.productcatalogservice.entity.Product;
import com.grocery.productcatalogservice.mapper.CategoryMapper;
import com.grocery.productcatalogservice.mapper.ProductMapper;
import com.grocery.productcatalogservice.repository.CategoryRepo;
import com.grocery.productcatalogservice.repository.ProductRepo;
import com.grocery.productcatalogservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto request) {

        if (categoryRepo.findByCategoryName(request.categoryName()).isPresent()) {
            throw new CategoryAlreadyExistsException(
                    "Category already exists: " + request.categoryName());
        }

        Category category = CategoryMapper.toEntity(request);
        Category savedCategory = categoryRepo.save(category);

        return CategoryMapper.toResponseDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {

        return categoryRepo.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
    }


    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(
                                ProductConstants.CATEGORY_NOT_FOUND + categoryId));

        return CategoryMapper.toResponseDto(category);
    }


    @Override
    @Transactional
    public CategoryResponseDto updateCategory(Long categoryId,
                                              CategoryRequestDto request) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(
                        ProductConstants.CATEGORY_NOT_FOUND + categoryId));

        category.setCategoryName(request.categoryName());
        category.setDescription(request.description());

        Category updated = categoryRepo.save(category);

        return CategoryMapper.toResponseDto(updated);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(
                        ProductConstants.CATEGORY_NOT_FOUND + categoryId));
        categoryRepo.delete(category);
    }

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto request) {

        if (productRepo.findBySku(request.sku()).isPresent()) {
            throw new ProductAlreadyExistsException(
                    "Product already exists with SKU : " + request.sku());
        }

        Category category = categoryRepo.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        ProductConstants.CATEGORY_NOT_FOUND + request.categoryId()));

        Product product = ProductMapper.toEntity(request, category);

        Product saved = productRepo.save(product);

        return ProductMapper.toResponseDto(saved);
    }


    @Override
    public List<ProductResponseDto> getAllProducts() {

        return productRepo.findAll()
                .stream()
                .map(ProductMapper::toResponseDto)
                .toList();
    }


    @Override
    public ProductResponseDto getProductById(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                               ProductConstants.PRODUCT_NOT_FOUND + productId));

        return ProductMapper.toResponseDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long productId,
                                            ProductRequestDto request) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                                ProductConstants.PRODUCT_NOT_FOUND + productId));


        Category category = categoryRepo.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                               ProductConstants.CATEGORY_NOT_FOUND + request.categoryId()));

        product.setProductName(request.productName());
        product.setSku(request.sku());
        product.setPrice(request.price());
        product.setActive(request.active());
        product.setImageUrl(request.imageUrl());
        product.setCategory(category);

        Product updated = productRepo.save(product);

        return ProductMapper.toResponseDto(updated);
    }


    @Override
    @Transactional
    public void deleteProduct(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                                ProductConstants.PRODUCT_NOT_FOUND + productId));

        productRepo.delete(product);
    }
}
