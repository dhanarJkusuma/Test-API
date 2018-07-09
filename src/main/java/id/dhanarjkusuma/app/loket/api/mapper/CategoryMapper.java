package id.dhanarjkusuma.app.loket.api.mapper;

import id.dhanarjkusuma.app.loket.config.MappingConfig;
import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.dtoapi.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public abstract class CategoryMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "slug", source = "slug")
    public abstract CategoryResponse categoryToCategoryResponse(Category category);
}
