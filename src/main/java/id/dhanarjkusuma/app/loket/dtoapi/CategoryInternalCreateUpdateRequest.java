package id.dhanarjkusuma.app.loket.dtoapi;


import javax.validation.constraints.NotNull;

public class CategoryInternalCreateUpdateRequest {

    @NotNull(message = "Category name cannot be null.")
    private String name;

    public CategoryInternalCreateUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
