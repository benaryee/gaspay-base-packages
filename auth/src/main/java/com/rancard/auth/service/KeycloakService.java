package com.rancard.auth.service;


import com.rancard.auth.config.KeycloakManager;
import com.rancard.auth.exception.UnauthorizedException;
import com.rancard.auth.model.dto.SignInDto;
import com.rancard.auth.model.dto.SignupDto;
import com.rancard.auth.model.enums.Channel;
import com.rancard.auth.model.payload.OAuthTokenResponse;
import com.rancard.auth.model.response.response.AccessTokenResponseDto;
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

    Keycloak adminKc = KeycloakManager.createKeycloakAdminInstance();


    public UserRepresentation registerUser(SignupDto signupDto) {

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(signupDto.getUsername());

        RoleRepresentation userRealmRole = null;
        switch (signupDto.getChannel()){
            case APP, USSD -> {
                userRealmRole = KeycloakManager.getRealm(adminKc).roles()
                        .get("USER").toRepresentation();
                user.setUsername(signupDto.getPhone());
            }
            case WEB -> {
                userRealmRole = KeycloakManager.getRealm(adminKc).roles()
                        .get("VENDOR").toRepresentation();
            }case INVITE -> {
                userRealmRole = KeycloakManager.getRealm(adminKc).roles()
                        .get("AGENT").toRepresentation();
            }

        }

        user.setEmail(signupDto.getEmail());
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());

        RealmResource realmResource = KeycloakManager.getRealm(adminKc);
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
        return KeycloakManager.getRealm(adminKc).roles().list();
    }


    public AccessTokenResponseDto authenticateUser(SignInDto signInDto) {

        Keycloak keycloak = KeycloakManager.createKeycloakUserInstance(signInDto.getUsername() , signInDto.getPassword());
        AccessTokenResponseDto tokenResponseDto = new AccessTokenResponseDto();


        try {
            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
            log.info("Token Response: {}", tokenResponse);

            if(tokenResponse != null){
                UserRepresentation userRepresentation = KeycloakManager.getRealm(adminKc).users().search(signInDto.getUsername()).get(0);
                String userId = userRepresentation.getId();
                List<RoleRepresentation> userRoles = KeycloakManager.getRealm(adminKc).users().get(userId).roles().realmLevel().listAll();

                for (RoleRepresentation role : userRoles) {
                    tokenResponseDto.getRoles().add(role.getName());
                }
            }

            log.info("Token Response: {}", tokenResponse);
        }catch(NotAuthorizedException notAuthorizedException){
            throw new UnauthorizedException("Invalid Credentials");
        }

        return tokenResponseDto;

    }
}
