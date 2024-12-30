package com.example.demo.dto;

import com.example.demo.config.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;

/**
 * A DTO representing a user, with his roles.
 */

public record AdminUserDTO(
        Long id,

        @NotBlank
        @Pattern(regexp = Constants.LOGIN_REGEX)
        @Size(min = 1, max = 50)
        String login,

        @Size(max = 50)
        String firstName,

        @Size(max = 50)
        String lastName,

        @Email
        @Size(min = 5, max = 254)
        String email,

        @Size(max = 256)
        String imageUrl,

        boolean activated,

        @Size(min = 2, max = 10)
        String langKey,

        String createdBy,

        Instant createdDate,

        String lastModifiedBy,

        Instant lastModifiedDate,

        Set<String> roles

) {
}