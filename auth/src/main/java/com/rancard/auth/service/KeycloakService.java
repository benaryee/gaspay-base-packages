package com.rancard.auth.service;


import com.rancard.auth.exception.UnauthorizedException;
import com.rancard.auth.model.dto.SignInDto;
import com.rancard.auth.model.dto.SignupDto;
import com.rancard.auth.model.enums.Channel;
import com.rancard.auth.model.payload.OAuthTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
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
        user.setEnabled(true);
        user.setUsername(signupDto.getUsername());

        RoleRepresentation userRealmRole = null;
        switch (signupDto.getChannel()){
            case APP, USSD -> {
                userRealmRole = keycloak.realm(realm).roles()
                        .get("USER").toRepresentation();
                user.setUsername(signupDto.getPhone());
            }
            case WEB -> {
                userRealmRole = keycloak.realm(realm).roles()
                        .get("VENDOR").toRepresentation();
            }

        }

        user.setEmail(signupDto.getEmail());
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersRessource = realmResource.users();

        log.info("Creating user: {}", user);

        Response response = usersRessource.create(user);

        String userId = CreatedResponseUtil.getCreatedId(response);
        //TODO -- Handle Error statuses

        // Call Keycloak API to create the user
        log.info("Keycloak response: {}", response);
        log.info("Response:{} {}", response.getStatus(), response.getStatusInfo());
        log.info("User created with userId: {}", userId);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(signupDto.getPassword());
        credentialRepresentation.setTemporary(false);

        // Set password credentials
        user.setCredentials(List.of(credentialRepresentation));
        UserResource userResource = usersRessource.get(userId);

        userResource.resetPassword(credentialRepresentation);

        userResource.roles().realmLevel()
                .add(Collections.singletonList(userRealmRole));

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            user.setId(userId);
            log.info("Created user: {}", userResource);
            return user;
        } else {
            throw new RuntimeException("Failed to create user: " + response.getStatusInfo().getReasonPhrase());
        }
    }
    //Testing Function
    public List<RoleRepresentation> getRoles() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
        return keycloak.realm(realm).roles().list();
    }

    public UserRepresentation authenticateUser2(SignInDto signInDto) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.PASSWORD)
                .username(signInDto.getUsername())
                .password(signInDto.getPassword())
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
        log.info("Token Response: {}", tokenResponse);
        UserRepresentation user = keycloak.realm(realm).users().search(signInDto.getUsername()).get(0);
        log.info("User Representation: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }


    public AccessTokenResponse authenticateUser(SignInDto signInDto) {

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .grantType(OAuth2Constants.PASSWORD)
                .username(signInDto.getUsername())
                .password(signInDto.getPassword())
                .build();


        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
        log.info("Token Response: {}", tokenResponse);

        try {
            tokenResponse = keycloak.tokenManager().getAccessToken();
            log.info("Token Response: {}", tokenResponse);
        }catch(NotAuthorizedException notAuthorizedException){
            throw new UnauthorizedException("Invalid Credentials");
        }

        if(tokenResponse != null) {
            return tokenResponse;
        }

        throw new UsernameNotFoundException("User not found");
    }
}
