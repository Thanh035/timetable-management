package com.example.demo.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && getRoles(authentication).noneMatch(RolesConstants.ANONYMOUS::equals);
    }

    /**
     * Checks if the current user has any of the roles.
     *
     * @param roles the roles to check.
     * @return true if the current user has any of the roles, false otherwise.
     */
    public static boolean hasCurrentUserAnyOfRoles(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (
                authentication != null && getRoles(authentication).anyMatch(role -> Arrays.asList(roles).contains(role))
        );
    }

    /**
     * Checks if the current user has none of the roles.
     *
     * @param roles the roles to check.
     * @return true if the current user has none of the roles, false otherwise.
     */
    public static boolean hasCurrentUserNoneOfRoles(String... roles) {
        return !hasCurrentUserAnyOfRoles(roles);
    }

    /**
     * Checks if the current user has a specific role.
     *
     * @param role the role to check.
     * @return true if the current user has the role, false otherwise.
     */
    public static boolean hasCurrentUserThisAuthority(String role) {
        return hasCurrentUserAnyOfRoles(role);
    }

    private static Stream<String> getRoles(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }
}
