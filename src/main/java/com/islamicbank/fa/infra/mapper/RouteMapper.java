package com.islamicbank.fa.infra.mapper;

import com.islamicbank.fa.api.model.upload.route.RouteDto;
import com.islamicbank.fa.repository.entity.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(source = "srcAirportCode", target = "routePK.source")
    @Mapping(source = "destAirportCode", target = "routePK.destination")
    @Mapping(source = "srcAirportId", target = "sourceAirport.airportId")
    @Mapping(source = "destAirportId", target = "destinationAirport.airportId")
    Route toModel(RouteDto routeDto);
}