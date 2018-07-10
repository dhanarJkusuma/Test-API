package id.dhanarjkusuma.app.loket.dtoservice;

import org.springframework.lang.Nullable;

import java.util.Date;

public class EventFilter {
    private String searchName;
    private String slugCategory;
    private String city;
    private Date filterDate;

    public EventFilter() {
    }

    public static EventFilter createFilter(@Nullable String searchName, @Nullable String slugCategory,@Nullable String city, @Nullable Date currentDate){
        EventFilter eventFilter = new EventFilter();
        eventFilter.setSearchName(searchName);
        eventFilter.setSlugCategory(slugCategory);
        eventFilter.setCity(city);
        eventFilter.setFilterDate(currentDate);
        return eventFilter;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSlugCategory() {
        return slugCategory;
    }

    public void setSlugCategory(String slugCategory) {
        this.slugCategory = slugCategory;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }
}
