package org.example.spendwiseapi.dto;

public record AuthResponse(
        String token,
        String tokenType
) {
}