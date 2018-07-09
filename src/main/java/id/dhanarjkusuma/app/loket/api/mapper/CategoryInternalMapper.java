package id.dhanarjkusuma.app.loket.api.mapper;

import id.dhanarjkusuma.app.loket.config.MappingConfig;
import id.dhanarjkusuma.app.loket.domain.Category;
import id.dhanarjkusuma.app.loket.dtoapi.CategoryInternalResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static id.dhanarjkusuma.app.loket.helper.Utils.formatGeneralDate;

@Mapper(config = MappingConfig.class)
public abstract class CategoryInternalMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "slug", source = "slug")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract CategoryInternalResponse categoryToCategoryInternalResponse(Category category);

    @AfterMapping
    protected void fillDateString(@MappingTarget CategoryInternalResponse response, Category category){
        response.setCreatedAt(formatGeneralDate(category.getCreatedAt()));
        response.setUpdatedAt(formatGeneralDate(category.getUpdatedAt()));
    }
}
