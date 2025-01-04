package com.islamicbank.fa.api.model.upload.airport.verifer;

import com.islamicbank.fa.api.model.upload.airport.AirportDto;
import com.opencsv.bean.BeanVerifier;

public class AirportBeanVerifier implements BeanVerifier<AirportDto> {
    @Override
    public boolean verifyBean(AirportDto airport) {
        return airport.getAirportId() != 0 &&
                !airport.getCity().isBlank();
    }
}