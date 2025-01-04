package com.islamicbank.fa.api.model.response;

public record TokenRefreshResponse(
        String accessToken,
        String refreshToken) {
}