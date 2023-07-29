package com.rancard.auth.service.impl;

import com.rancard.auth.models.dto.SignupDto;
import com.rancard.auth.models.mongo.User;
import com.rancard.auth.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Repository

public class KeycloakServiceImpl  implements KeycloakService {
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public UserRepresentation registerUser(String username, String email, String password) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        // Create a new UserRepresentation
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        // Set password credentials
        user.setCredentials(List.of(credentialRepresentation));

        // Call Keycloak API to create the user
        Response response = keycloak.realm(realm).users().create(user);
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            // User creation successful, return the created UserRepresentation
            return response.readEntity(UserRepresentation.class);
        } else {
            // Handle failure case (e.g., user already exists, Keycloak server error, etc.)
            throw new RuntimeException("Failed to create user: " + response.getStatusInfo().getReasonPhrase());
        }
    }



}
