package id.dhanarjkusuma.app.loket.service;

import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    List<Category> fetchCategories();
    Category getCategory(Long id) throws CategoryNotFoundException;
    Category getCategoryBySlug(String slug) throws CategoryNotFoundException;
    Category update(Long id, Category category) throws CategoryNotFoundException;
    void destroy(Long id) throws CategoryNotFoundException;
}
