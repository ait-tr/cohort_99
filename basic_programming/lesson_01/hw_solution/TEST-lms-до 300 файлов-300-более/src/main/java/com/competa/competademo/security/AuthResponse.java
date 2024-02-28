package com.competa.competademo.security;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Andrej Reutow
 * created on 25.11.2023
 */
@Schema(name = "AuthResponse", description = "response details")
public record AuthResponse(String message) {
}
