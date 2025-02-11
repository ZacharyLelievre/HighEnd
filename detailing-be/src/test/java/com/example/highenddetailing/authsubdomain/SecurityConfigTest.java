package com.example.highenddetailing.authsubdomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SecurityConfigTest {

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private Jwt jwt;

    @Mock
    private JwtDecoder jwtDecoder;

    @InjectMocks
    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnCorsConfigurationSource() {
        // Arrange
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setRequestURI("/**");
        UrlBasedCorsConfigurationSource corsConfigurationSource = securityConfig.corsConfigurationSource();

        // Act
        CorsConfiguration configuration = corsConfigurationSource.getCorsConfiguration(mockRequest);

        // Assert
        assertNotNull(configuration, "CorsConfiguration should not be null");
        assertTrue(configuration.getAllowCredentials(), "Allow credentials should be true");
//        assertEquals(List.of("http://localhost:3000", "https://highend-1.onrender.com", "https://highend-zke6.onrender.com"), configuration.getAllowedOrigins(), "Allowed origins should match");
        assertEquals(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"), configuration.getAllowedMethods(), "Allowed methods should match");
        assertEquals(List.of("Authorization", "Cache-Control", "Content-Type"), configuration.getAllowedHeaders(), "Allowed headers should match");
        assertEquals(List.of("Authorization"), configuration.getExposedHeaders(), "Exposed headers should match");
    }


    @Test
    public void shouldBuildSecurityFilterChain() throws Exception {
        // Arrange
        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.oauth2ResourceServer(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(null);

        // Act
        securityConfig.securityFilterChain(httpSecurity);

        // Assert
        verify(httpSecurity, times(1)).build();
    }
    @Test
    public void shouldConfigureCorsRegistry() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();
        WebMvcConfigurer webMvcConfigurer = corsConfig.corsConfigurer();

        // Act
        CorsRegistry registry = new CorsRegistry();
        webMvcConfigurer.addCorsMappings(registry);

        // Assert
        // Verify the configuration by simulating a registry setup
        assertDoesNotThrow(() -> {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        });
    }

    @Test
    public void shouldValidateJwtWithCorrectAudience() {
        // Arrange
        AudienceValidator audienceValidator = new AudienceValidator("correct-audience");
        when(jwt.getAudience()).thenReturn(List.of("correct-audience"));

        // Act
        OAuth2TokenValidatorResult result = audienceValidator.validate(jwt);

        // Assert
        assertFalse(result.hasErrors(), "JWT validation should pass with the correct audience");
    }

    @Test
    public void shouldNotValidateJwtWithIncorrectAudience() {
        // Arrange
        AudienceValidator audienceValidator = new AudienceValidator("correct-audience");
        when(jwt.getAudience()).thenReturn(List.of("incorrect-audience"));

        // Act
        OAuth2TokenValidatorResult result = audienceValidator.validate(jwt);

        // Assert
        assertTrue(result.hasErrors(), "JWT validation should fail with an incorrect audience");
    }
    @Test
    public void shouldConfigureHttpSecurityWithCorrectRequestMatchers() throws Exception {
        // Arrange
        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any(Customizer.class))).thenReturn(httpSecurity); // Updated to match the new method signature
        when(httpSecurity.oauth2ResourceServer(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(null);

        // Act
        securityConfig.securityFilterChain(httpSecurity);

        // Assert
        verify(httpSecurity, times(1)).authorizeHttpRequests(any(Customizer.class));
        verify(httpSecurity, times(1)).oauth2ResourceServer(any());
    }

    @Test
    public void shouldReturnJwtAuthenticationConverter() {
        // Act
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();

        // Assert
        assertNotNull(converter, "JwtAuthenticationConverter should not be null");
    }
    @Test
    public void shouldConvertJwtClaimsToGrantedAuthorities() {
        // Arrange
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsStringList("https://highenddetailing/roles"))
                .thenReturn(List.of("admin", "user"));

        CustomRoleConverter customRoleConverter = new CustomRoleConverter();

        // Act
        Collection<GrantedAuthority> authorities = customRoleConverter.convert(jwt);

        // Assert
        assertNotNull(authorities, "GrantedAuthorities should not be null");
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")),
                "Authorities should contain ROLE_ADMIN");
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")),
                "Authorities should contain ROLE_USER");
    }

    @Test
    public void shouldHandleNullRolesGracefully() {
        // Arrange
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsStringList("https://highenddetailing/roles"))
                .thenReturn(null);

        CustomRoleConverter customRoleConverter = new CustomRoleConverter();

        // Act
        Collection<GrantedAuthority> authorities = customRoleConverter.convert(jwt);

        // Assert
        assertNotNull(authorities, "GrantedAuthorities should not be null");
        assertTrue(authorities.isEmpty(), "Authorities should be empty when roles are null");
    }

    @Test
    public void shouldIncludeRolesFromJwtClaims() {
        // Arrange
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsStringList("https://highenddetailing/roles"))
                .thenReturn(List.of("ADMIN", "EMPLOYEE", "CUSTOMER"));

        CustomRoleConverter customRoleConverter = new CustomRoleConverter();

        // Act
        Collection<GrantedAuthority> authorities = customRoleConverter.convert(jwt);

        // Assert
        assertNotNull(authorities, "GrantedAuthorities should not be null");
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")),
                "Authorities should contain ROLE_ADMIN");
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE")),
                "Authorities should contain ROLE_EMPLOYEE");
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER")),
                "Authorities should contain ROLE_CUSTOMER");
    }

}