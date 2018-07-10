package id.dhanarjkusuma.app.loket.api.mapper;

import id.dhanarjkusuma.app.loket.config.MappingConfig;
import id.dhanarjkusuma.app.loket.domain.Location;
import id.dhanarjkusuma.app.loket.dtoapi.LocationCreateRequest;
import id.dhanarjkusuma.app.loket.dtoapi.LocationResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Date;

import static id.dhanarjkusuma.app.loket.helper.Utils.formatGeneralDateTime;

@Mapper(config = MappingConfig.class)
public abstract class LocationMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "createdAt", source = "updatedAt")
    public abstract LocationResponse locationToLocationResponse(Location location);

    @AfterMapping
    protected void fillCreatedAt(@MappingTarget LocationResponse response, Location location){
        response.setCreatedAt(formatGeneralDateTime(location.getCreatedAt()));
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "event", ignore = true)
    public abstract Location locationRequestToLocation(LocationCreateRequest request);

    @AfterMapping
    protected void fillBlankField(@MappingTarget Location location, LocationCreateRequest request){
        location.setCreatedAt(new Date());
        location.setUpdatedAt(new Date());
        location.setIsDeleted(false);
    }
}
