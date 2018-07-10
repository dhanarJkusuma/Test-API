package id.dhanarjkusuma.app.loket.api;

import id.dhanarjkusuma.app.loket.api.mapper.CategoryInternalMapper;
import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.dtoapi.CategoryInternalCreateUpdateRequest;
import id.dhanarjkusuma.app.loket.dtoapi.CategoryInternalResponse;
import id.dhanarjkusuma.app.loket.dtoapi.ErrorResponseMessage;
import id.dhanarjkusuma.app.loket.dtoapi.ErrorResponseRequest;
import id.dhanarjkusuma.app.loket.exception.CategoryNotFoundException;
import id.dhanarjkusuma.app.loket.helper.Utils;
import id.dhanarjkusuma.app.loket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/internal/category")
public class CategoryInternalApi {

    private CategoryService categoryService;
    private CategoryInternalMapper mapper;

    @Autowired
    public CategoryInternalApi(
            CategoryService categoryService,
            CategoryInternalMapper mapper
    ) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public CategoryInternalResponse create(@Valid @RequestBody CategoryInternalCreateUpdateRequest request){
        Category category = new Category();
        category.setName(request.getName());
        return mapper.categoryToCategoryInternalResponse(categoryService.create(category));
    }

    @GetMapping("/get_info")
    public List<CategoryInternalResponse> fetch(){
        List<Category> categories = categoryService.fetchCategories();
        return categories
                .stream()
                .map(mapper::categoryToCategoryInternalResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/get_detail")
    public CategoryInternalResponse findOne(
            @RequestParam(name = "id") Long id
    ){
        Category category = categoryService.getCategory(id);
        return mapper.categoryToCategoryInternalResponse(category);
    }

    @PutMapping("/update")
    public CategoryInternalResponse update(
            @RequestParam(name = "id") Long id,
            @Valid @RequestBody CategoryInternalCreateUpdateRequest request
    ){
        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(Utils.toSlug(category.getName()));
        return mapper.categoryToCategoryInternalResponse(categoryService.update(id, category));
    }

    @DeleteMapping("/delete")
    public ResponseEntity destroy( @RequestParam(name = "id") Long id){
        categoryService.destroy(id);
        return ResponseEntity.noContent().build();
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseMessage handleNotFoundException(CategoryNotFoundException e){
        return new ErrorResponseMessage(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseRequest handleException(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        Map<String, List<String>> errorFields = new HashMap<>();
        for(FieldError fieldError: errors){
            List<String> errMessages = errorFields.get(fieldError.getField());
            if(errMessages == null){
               errMessages = new ArrayList<>();
            }
            errMessages.add(fieldError.getDefaultMessage());
            errorFields.put(fieldError.getField(), errMessages);
        }
        ErrorResponseRequest response = new ErrorResponseRequest();
        response.setMessage("Error while Creating/Updating a category");
        response.setErrors(errorFields);
        return response;
    }

}
