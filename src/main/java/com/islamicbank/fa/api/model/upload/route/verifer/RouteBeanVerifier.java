package com.islamicbank.fa.api.model.upload.route.verifer;

import com.islamicbank.fa.api.model.upload.route.RouteDto;
import com.opencsv.bean.BeanVerifier;

public class RouteBeanVerifier implements BeanVerifier<RouteDto> {
    @Override
    public boolean verifyBean(RouteDto route) {
        return route.getSrcAirportId() != 0 &&
                   route.getDestAirportId() != 0 &&
                   route.getAirlineId() != 0;
    }
}