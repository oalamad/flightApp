package com.islamicbank.fa.api.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public record CountryResponse(@JsonProperty int id,
                              @JsonProperty String name) {
}
