package com.rancard.auth.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.io.InputStream;
import java.util.Properties;

public class KeycloakManager {


    private static RealmResource realm;

    public static Keycloak createKeycloakAdminInstance() {
        Properties properties = loadProperties("keycloak.properties");

        return KeycloakBuilder.builder()
                .serverUrl(properties.getProperty("keycloak.server.url"))
                .realm(properties.getProperty("keycloak.realm"))
                .clientId(properties.getProperty("keycloak.client.id"))
                .username(properties.getProperty("keycloak.admin.username"))
                .password(properties.getProperty("keycloak.admin.password"))
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

    public static Keycloak createKeycloakUserInstance(String username, String password) {
        Properties properties = loadProperties("keycloak.properties");

        return KeycloakBuilder.builder()
                .serverUrl(properties.getProperty("keycloak.server.url"))
                .realm(properties.getProperty("keycloak.realm"))
                .clientId(properties.getProperty("keycloak.client.id"))
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

    private static Properties loadProperties(String filename) {
        Properties properties = new Properties();
        try (InputStream input = KeycloakManager.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return properties;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static RealmResource getRealm(Keycloak keycloak){
        Properties properties = loadProperties("keycloak.properties");
        return keycloak.realm(properties.getProperty("keycloak.realm"));
    }

}
