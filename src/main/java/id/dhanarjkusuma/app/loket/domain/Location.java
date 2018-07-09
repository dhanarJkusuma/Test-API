package id.dhanarjkusuma.app.loket.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToOne(mappedBy = "location")
    private Event event;

    public Location() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isDeleted = false;
    }

    private Location(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setCity(builder.city);
        setAddress(builder.address);
        setLatitude(builder.latitude);
        setLongitude(builder.longitude);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        isDeleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    public static final class Builder {
        private Long id;
        private String name;
        private String city;
        private String address;
        private Double latitude;
        private Double longitude;
        private Date createdAt;
        private Date updatedAt;

        public Builder() {
            createdAt = new Date();
            updatedAt = new Date();
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder latitude(Double val) {
            latitude = val;
            return this;
        }

        public Builder longitude(Double val) {
            longitude = val;
            return this;
        }

        public Builder createdAt(Date val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(Date val) {
            updatedAt = val;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }
}
