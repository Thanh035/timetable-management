package com.example.demo.config;

import com.example.demo.security.CookieCsrfFilter;
import com.example.demo.security.RolesConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

//    private final RememberMeServices rememberMeServices;
//
//    public SecurityConfiguration(RememberMeServices rememberMeServices) {
//        this.rememberMeServices = rememberMeServices;
//    }
//
//    @Value("${my-config.security.remember-me.key}")
//    private String rememberMeKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf ->
                        csrf
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                // See https://stackoverflow.com/q/74447118/65681
                                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authz ->
                        // prettier-ignore
                        authz
                                .requestMatchers(mvc.pattern("/api/admin/**")).hasAuthority(RolesConstants.ADMIN)
//                                .requestMatchers(mvc.pattern("/api/**")).authenticated()
                                .requestMatchers(mvc.pattern("/api/**")).permitAll()
                                .requestMatchers(mvc.pattern("/websocket/**")).authenticated()
                                .requestMatchers(mvc.pattern("/v3/api-docs/**")).hasAuthority(RolesConstants.ADMIN)
                                .requestMatchers(mvc.pattern("/management/health")).permitAll()
                                .requestMatchers(mvc.pattern("/management/health/**")).permitAll()
                                .requestMatchers(mvc.pattern("/management/info")).permitAll()
                                .requestMatchers(mvc.pattern("/management/prometheus")).permitAll()
                                .requestMatchers(mvc.pattern("/management/**")).hasAuthority(RolesConstants.ADMIN)

                                .requestMatchers(mvc.pattern("/admin/**")).permitAll()
                                .requestMatchers(mvc.pattern("/login")).permitAll()
                                .requestMatchers(mvc.pattern("/index.html")).permitAll()
                                .requestMatchers(mvc.pattern("/template/**")).permitAll()
                )
//                .rememberMe(rememberMe ->
//                        rememberMe
//                                .rememberMeServices(rememberMeServices)
//                                .rememberMeParameter("remember-me")
//                                .key(rememberMeKey)
//                )
                .exceptionHandling(exceptionHanding ->
                exceptionHanding.defaultAuthenticationEntryPointFor(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new OrRequestMatcher(antMatcher("/websocket/**"), antMatcher("/api/**"))
                )
        )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/")
                                .loginProcessingUrl("/api/authentication")
                                .successHandler((request, response, authentication) -> response.setStatus(HttpStatus.OK.value()))
                                .failureHandler((request, response, exception) -> response.setStatus(HttpStatus.UNAUTHORIZED.value()))
                                .permitAll()
                )
                .logout(logout ->
                        logout.logoutUrl("/api/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).permitAll()
                );
        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}
