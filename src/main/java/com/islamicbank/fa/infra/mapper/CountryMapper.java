package com.islamicbank.fa.infra.mapper;

import com.islamicbank.fa.api.model.response.CountryResponse;
import com.islamicbank.fa.repository.entity.Country;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryResponse toView(Country country);

    Set<CountryResponse> toViews(Set<Country> countries);
}