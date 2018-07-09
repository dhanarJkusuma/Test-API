package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.repository.CategoryRepository;
import id.dhanarjkusuma.app.loket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> fetchCategories() {
        return categoryRepository.findByIsDeletedFalseByOrderByNameAsc();
    }

    @Override
    public Category getCategory(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findByIdAndIsDeletedFalse(id);
        return category.orElseThrow(() -> new CategoryNotFoundException("Unknown category with id : " + id));
    }

    @Override
    public Category getCategoryBySlug(String slug) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findFirstBySlugAndIsDeletedFalse(slug);
        return category.orElseThrow(() -> new CategoryNotFoundException("Unknown category with slug : " + slug));
    }

    @Override
    public Category update(Long id, Category category) throws CategoryNotFoundException {
        Category savedCategory = categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new CategoryNotFoundException("Unknown category with id : " + id));
        savedCategory.setName(category.getName());
        savedCategory.setSlug(category.getSlug());
        savedCategory.setUpdatedAt(new Date());
        return categoryRepository.save(savedCategory);
    }

    @Override
    public void destroy(Long id) throws CategoryNotFoundException {
        Category existCategory = categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new CategoryNotFoundException("Unknown category with id : " + id));
        existCategory.setIsDeleted(true);
        categoryRepository.save(existCategory);
    }
}
