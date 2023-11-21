package com.gaspay.ussdapp.controller;

import com.gaspay.ussdapp.config.UssdKeycloakAuthConfig;
import com.gaspay.ussdapp.model.mongo.UssdMenu;
import com.gaspay.ussdapp.model.request.UssdMenuRequest;
import com.gaspay.ussdapp.services.UssdMenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ussd/menu")
public class MenuController {

    private final UssdMenuService ussdMenuService;
    private final UssdKeycloakAuthConfig  ussdKeycloakAuthConfig;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addUssdMenu(@RequestBody UssdMenuRequest ussdMenuRequest , HttpServletRequest request){
        String sessionId = request.getSession().getId();
        log.info("[{}] about to add menu with request details : {}", sessionId, ussdMenuRequest.toString());
        UssdMenu menu = ussdMenuService.addMenu(ussdMenuRequest,sessionId);
        return ResponseEntity.ok().body(menu);
    }

    @GetMapping
    public void testAuth(){
        ussdKeycloakAuthConfig.getKeycloakAccessToken();
    }

}
