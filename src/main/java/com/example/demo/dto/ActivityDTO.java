package com.example.demo.dto;

import java.time.Instant;

public record ActivityDTO(
        String sessionId,
        String userLogin,
        String ipAddress,
        String page,
        Instant time
) {
}