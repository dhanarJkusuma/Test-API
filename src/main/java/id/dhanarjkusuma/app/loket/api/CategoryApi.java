package id.dhanarjkusuma.app.loket.api;

import id.dhanarjkusuma.app.loket.api.mapper.CategoryMapper;
import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.dtoapi.CategoryResponse;
import id.dhanarjkusuma.app.loket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryApi {

    private CategoryService categoryService;
    private CategoryMapper mapper;

    @Autowired
    public CategoryApi(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<CategoryResponse> fetchCategories(){
        List<Category> categories = categoryService.fetchCategories();
        return categories
                .stream()
                .map(mapper::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }

}
