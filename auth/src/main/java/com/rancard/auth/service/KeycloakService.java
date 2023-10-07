package com.rancard.auth.service;


import com.rancard.auth.model.dto.SignInDto;
import com.rancard.auth.model.dto.SignupDto;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public UserRepresentation registerUser(SignupDto signupDto) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();

        // Create a new UserRepresentation
        UserRepresentation user = new UserRepresentation();

        switch (signupDto.getChannel()){
            case APP, USSD -> {
                user.setRealmRoles(List.of("user"));
                user.setUsername(signupDto.getPhone());
            }
            case WEB -> {
                user.setRealmRoles(List.of("vendor"));
                user.setUsername(signupDto.getUsername());
            }

        }

        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());

        user.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(signupDto.getPassword());
        credentialRepresentation.setTemporary(false);
        // Set password credentials
        user.setCredentials(List.of(credentialRepresentation));

        log.info("Creating user: {}", user);
        // Call Keycloak API to create the user
        Response response = keycloak.realm(realm).users().create(user);
        log.info("Keycloak response: {}", response);

        log.info("Response:{} {} %n", response.getStatus(), response.getStatusInfo());

        String userId = CreatedResponseUtil.getCreatedId(response);

        System.out.printf("User created with userId: %s%n", userId);


        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {

            user.setId(userId);
            log.info("Created user: {}", user);
            return user;
        } else {
            // Handle failure case (e.g., user already exists, Keycloak server error, etc.)
            throw new RuntimeException("Failed to create user: " + response.getStatusInfo().getReasonPhrase());
        }
    }


    //Testing Function
    public void getRoles() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();

        List<RoleRepresentation> roles = keycloak.realm(realm).roles().list();
        log.info("Roles: {}", roles);
    }

    public UserRepresentation authenticateUser(SignInDto signInDto) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.PASSWORD)
                .username(signInDto.getUsername())
                .password(signInDto.getPassword())
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().grantToken();
        log.info("Token Response: {}", tokenResponse);
        UserRepresentation user = keycloak.realm(realm).users().search(signInDto.getUsername()).get(0);
        log.info("User Representation: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
