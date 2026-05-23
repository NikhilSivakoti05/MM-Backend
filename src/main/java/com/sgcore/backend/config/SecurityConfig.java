package com.sgcore.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.IF_REQUIRED
                    )
            )
            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(
                            "/",
                            "/auth/login",
                            "/auth/logout",
                            "/auth/verify",
                            "/auth/forgot-password",
                            "/auth/reset-password",
                            "/uploads/**"
                    ).permitAll()

                    .requestMatchers(HttpMethod.GET,
                            "/api/services",
                            "/api/services/**",
                            "/api/products",
                            "/api/products/**",
                            "/api/jobs",
                            "/api/jobs/**",
                            "/api/portfolio",
                            "/api/portfolio/**",
                            "/api/testimonials",
                            "/api/testimonials/**",
                            "/api/stats",
                            "/api/stats/**",
                            "/api/clients",
                            "/api/clients/**",
                            "/api/questions/**"
                    ).permitAll()

                    .requestMatchers(HttpMethod.POST,
                            "/api/applications",
                            "/api/applications/**",
                            "/api/contact",
                            "/api/contact/**",
                            "/api/newsletter",
                            "/api/newsletter/**"
                    ).permitAll()

                    .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")

                    .requestMatchers(HttpMethod.GET,
                            "/api/applications",
                            "/api/applications/**",
                            "/api/contact",
                            "/api/contact/**",
                            "/api/newsletter",
                            "/api/newsletter/**",
                            "/api/files",
                            "/api/files/**"
                    ).hasRole("ADMIN")

                    .requestMatchers(HttpMethod.POST, "/api/**")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/**")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/**")
                    .hasRole("ADMIN")

                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                    .anyRequest().permitAll()
            )
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .logout(logout -> logout
                    .logoutUrl("/auth/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(
                            (request, response, authentication) ->
                                    response.setStatus(200)
                    )
            )
            .exceptionHandling(ex ->
                    ex.authenticationEntryPoint(
                            (request, response, authException) -> {
                                response.setStatus(401);
                                response.getWriter().write("Unauthorized");
                            })
            );

        return http.build();
    }
}
