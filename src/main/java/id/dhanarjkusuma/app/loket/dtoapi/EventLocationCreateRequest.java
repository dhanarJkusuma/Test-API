package id.dhanarjkusuma.app.loket.dtoapi;

import javax.validation.constraints.NotNull;

public class EventLocationCreateRequest {

    @NotNull(message = "Location name cannot be null.")
    private String name;

    @NotNull(message = "Location city cannot be null.")
    private String city;

    @NotNull(message = "Location address cannot be null.")
    private String address;

    @NotNull(message = "Location latitude cannot be null.")
    private Double latitude;

    @NotNull(message = "Location longitude cannot be null.")
    private Double longitude;

    public EventLocationCreateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
