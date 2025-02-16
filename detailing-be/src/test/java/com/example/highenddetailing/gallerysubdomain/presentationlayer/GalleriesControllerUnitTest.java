package com.example.highenddetailing.gallerysubdomain.presentationlayer;

import com.example.highenddetailing.gallerysubdomain.businesslayer.GalleryService;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryController;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GalleryController.class)
public class GalleriesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GalleryService galleryService;

    private List<GalleryResponseModel> galleryResponseModels;

    @BeforeEach
    public void setup() {
        galleryResponseModels = Arrays.asList(
                GalleryResponseModel.builder()
                        .galleryId("1")
                        .description("Description of gallery 1")
                        .imageUrl("/images/gallery/gallery1.jpg")
                        .build(),
                GalleryResponseModel.builder()
                        .galleryId("2")
                        .description("Description of gallery 2")
                        .imageUrl("/images/gallery/gallery2.jpg")
                        .build()
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    public void whenGetAllGalleries_thenReturnAllGalleries() throws Exception {
        when(galleryService.getAllGalleries()).thenReturn(galleryResponseModels);

        mockMvc.perform(get("/api/galleries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].description").value("Description of gallery 1"))
                .andExpect(jsonPath("$[1].description").value("Description of gallery 2"));
    }
//
//    @Test
//    public void whenDeleteGallery_thenNoContent() throws Exception {
//        String galleryId = "1";
//        doNothing().when(galleryService).deleteImage(galleryId);
//
//        // Mock JWT
//        Jwt jwt = Jwt.withTokenValue("token")
//                .header("alg", "none")
//                .claim("roles", Collections.singletonList("ADMIN"))
//                .issuer("https://dev-vmtwqb6p6lr3if0d.us.auth0.com/")
//                .issuedAt(Instant.now())
//                .expiresAt(Instant.now().plusSeconds(3600))
//                .build();
//
//        // JwtAuthenticationToken
//        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwt, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//
//        // Mock HttpServletRequest
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setScheme("http");
//        request.setServerName("localhost"); //use your backend url here.
//        request.setServerPort(8081); // Standard HTTPS port
//        request.setRequestURI("/api/galleries/" + galleryId);
//        request.setMethod("DELETE");
//
//        mockMvc.perform(delete("/api/galleries/" + galleryId)
//                        .with(req -> {
//                            req.setUserPrincipal(authenticationToken);
//                            return req;
//                        })
//                        .requestAttr(MockHttpServletRequest.class.getName(), request)) // Add the request attributes.
//                .andExpect(status().isNoContent());
//
//        verify(galleryService, times(1)).deleteImage(galleryId);
//    }
//    @Test
//    public void whenDeleteGallery_galleryNotFound_thenNotFound() throws Exception {
//        String galleryId = "nonexistent";
//        doThrow(new RuntimeException("Gallery not found")).when(galleryService).deleteImage(galleryId);
//
//        // Mock JWT
//        Jwt jwt = Jwt.withTokenValue("token")
//                .header("alg", "none")
//                .claim("sub", "testuser") // Add the 'sub' claim
//                .claim("roles", Collections.singletonList("ROLE_ADMIN"))
//                .issuer("https://dev-vmtwqb6p6lr3if0d.us.auth0.com/")
//                .issuedAt(Instant.now())
//                .expiresAt(Instant.now().plusSeconds(3600))
//                .build();
//
//        // JwtAuthenticationToken
//        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwt, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//
//        mockMvc.perform(delete("/api/galleries/" + galleryId)
//                        .with(request -> {
//                            request.setUserPrincipal(authenticationToken);
//                            return request;
//                        }))
//                .andExpect(status().isNotFound());
//
//        verify(galleryService, times(1)).deleteImage(galleryId);
//    }
}