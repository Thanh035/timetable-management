package com.example.demo.dto;

/**
 * A DTO representing a password change required data - current and new password.
 */
public record PasswordChangeDTO(
        String currentPassword,
        String newPassword
) { }
