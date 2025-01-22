//package com.example.highenddetailing.authsubdomain;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//@Profile("test")
//public class TestSecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        return http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing
//                .authorizeExchange(exchange -> exchange.anyExchange().permitAll()) // Allow all requests
//                .build();
//    }
//}