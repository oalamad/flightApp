package com.islamicbank.fa.infra.mapper;

import com.islamicbank.fa.api.model.response.AirportResponse;
import com.islamicbank.fa.api.model.upload.airport.AirportDto;
import com.islamicbank.fa.repository.entity.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirportMapper {

    @Mapping(target = "destinationRoutes", ignore = true)
    @Mapping(target = "sourceRoutes", ignore = true)
    @Mapping(target = "cityName", source = "city")
    @Mapping(target = "countryName", source = "country")
    @Mapping(target = "city.id", source = "cityId")
    @Mapping(target = "country.id", source = "countryId")
    Airport toModel(AirportDto airportDto);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "icao", expression = "java(null)")
    @Mapping(target = "city", source = "airport.cityName")
    @Mapping(target = "country", source = "airport.countryName")
    @Mapping(target = "airport", source = "airport.name")
    AirportResponse toTripView(Airport airport, int value);

    @Mapping(target = "id", source = "airportId")
    @Mapping(target = "city", source = "cityName")
    @Mapping(target = "country", source = "countryName")
    @Mapping(target = "airport", source = "name")
    AirportResponse toView(Airport airport);

    List<AirportResponse> toView(List<Airport> airport);
}