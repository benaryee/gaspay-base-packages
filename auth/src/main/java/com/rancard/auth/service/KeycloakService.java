package com.rancard.auth.service;

import com.rancard.auth.models.dto.SignupDto;
import com.rancard.auth.models.mongo.User;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    UserRepresentation registerUser (String username , String email, String password);

}
