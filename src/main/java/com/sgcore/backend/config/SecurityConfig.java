//package com.sgcore.backend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//            throws Exception {
//
//        http
//            .cors(Customizer.withDefaults())
//            .csrf(csrf -> csrf.disable())
//            .sessionManagement(session ->
//                    session.sessionCreationPolicy(
//                            SessionCreationPolicy.IF_REQUIRED
//                    )
//            )
//            .authorizeHttpRequests(auth -> auth
//
//                    .requestMatchers(
//                            "/",
//                            "/auth/login",
//                            "/auth/logout",
//                            "/auth/verify",
//                            "/auth/forgot-password",
//                            "/auth/reset-password",
//                            "/uploads/**"
//                    ).permitAll()
//
//                    .requestMatchers(HttpMethod.GET,
//                            "/api/services",
//                            "/api/services/**",
//                            "/api/products",
//                            "/api/products/**",
//                            "/api/jobs",
//                            "/api/jobs/**",
//                            "/api/portfolio",
//                            "/api/portfolio/**",
//                            "/api/testimonials",
//                            "/api/testimonials/**",
//                            "/api/stats",
//                            "/api/stats/**",
//                            "/api/clients",
//                            "/api/clients/**",
//                            "/api/questions/**"
//                    ).permitAll()
//
//                    .requestMatchers(HttpMethod.POST,
//                            "/api/applications",
//                            "/api/applications/**",
//                            "/api/contact",
//                            "/api/contact/**",
//                            "/api/newsletter",
//                            "/api/newsletter/**"
//                    ).permitAll()
//
//                    .requestMatchers("/api/admin/**")
//                    .hasRole("ADMIN")
//
//                    .requestMatchers(HttpMethod.GET,
//                            "/api/applications",
//                            "/api/applications/**",
//                            "/api/contact",
//                            "/api/contact/**",
//                            "/api/newsletter",
//                            "/api/newsletter/**",
//                            "/api/files",
//                            "/api/files/**"
//                    ).hasRole("ADMIN")
//
//                    .requestMatchers(HttpMethod.POST, "/api/**")
//                    .hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/api/**")
//                    .hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/**")
//                    .hasRole("ADMIN")
//
//                    .requestMatchers("/admin/**")
//                    .hasRole("ADMIN")
//
//                    .anyRequest().permitAll()
//            )
//            .formLogin(form -> form.disable())
//            .httpBasic(httpBasic -> httpBasic.disable())
//            .logout(logout -> logout
//                    .logoutUrl("/auth/logout")
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID")
//                    .logoutSuccessHandler(
//                            (request, response, authentication) ->
//                                    response.setStatus(200)
//                    )
//            )
//            .exceptionHandling(ex ->
//                    ex.authenticationEntryPoint(
//                            (request, response, authException) -> {
//                                response.setStatus(401);
//                                response.getWriter().write("Unauthorized");
//                            })
//            );
//
//        return http.build();
//    }
//}
// for real hosting 
package com.sgcore.backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

            // Enable CORS
            .cors(Customizer.withDefaults())

            // Disable CSRF for REST APIs
            .csrf(csrf -> csrf.disable())

            // Stateless session for JWT/Auth APIs
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            // Route Security
            .authorizeHttpRequests(auth -> auth

                    // Public Authentication APIs
                    .requestMatchers(
                            "/",
                            "/auth/login",
                            "/auth/logout",
                            "/auth/verify",
                            "/auth/forgot-password",
                            "/auth/reset-password",
                            "/uploads/**"
                    ).permitAll()

                    // Public GET APIs
                    .requestMatchers(
                            HttpMethod.GET,

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

                    // Public POST APIs
                    .requestMatchers(
                            HttpMethod.POST,

                            "/api/applications",
                            "/api/applications/**",

                            "/api/contact",
                            "/api/contact/**",

                            "/api/newsletter",
                            "/api/newsletter/**"

                    ).permitAll()

                    // Admin APIs
                    .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")

                    // Admin GET APIs
                    .requestMatchers(
                            HttpMethod.GET,

                            "/api/applications",
                            "/api/applications/**",

                            "/api/contact",
                            "/api/contact/**",

                            "/api/newsletter",
                            "/api/newsletter/**",

                            "/api/files",
                            "/api/files/**"

                    ).hasRole("ADMIN")

                    // Admin POST APIs
                    .requestMatchers(HttpMethod.POST, "/api/**")
                    .hasRole("ADMIN")

                    // Admin PUT APIs
                    .requestMatchers(HttpMethod.PUT, "/api/**")
                    .hasRole("ADMIN")

                    // Admin DELETE APIs
                    .requestMatchers(HttpMethod.DELETE, "/api/**")
                    .hasRole("ADMIN")

                    // Admin frontend routes
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                    // Everything else requires authentication
                    .anyRequest().authenticated()
            )

            // Disable default login page
            .formLogin(form -> form.disable())

            // Disable basic auth popup
            .httpBasic(httpBasic -> httpBasic.disable())

            // Logout config
            .logout(logout -> logout

                    .logoutUrl("/auth/logout")

                    .invalidateHttpSession(true)

                    .deleteCookies("JSESSIONID")

                    .logoutSuccessHandler(
                            (request, response, authentication) ->
                                    response.setStatus(200)
                    )
            )

            // Security headers
            .headers(headers -> headers

                    .frameOptions(frame -> frame.sameOrigin())

                    .contentSecurityPolicy(csp ->
                            csp.policyDirectives(
                                    "default-src 'self'; " +
                                    "img-src 'self' data: https:; " +
                                    "script-src 'self' 'unsafe-inline'; " +
                                    "style-src 'self' 'unsafe-inline';"
                            )
                    )
            )

            // Unauthorized handler
            .exceptionHandling(ex ->
                    ex.authenticationEntryPoint(
                            (request, response, authException) -> {

                                response.setStatus(401);

                                response.getWriter()
                                        .write("Unauthorized");
                            })
            );

        return http.build();
    }

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        // Replace with your frontend domain
        configuration.setAllowedOrigins(List.of(

                "http://localhost:5173",

                "https://machine-mechanics-frontend.vercel.app/"

        ));

        configuration.setAllowedMethods(List.of(

                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"

        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}