package com.rancard.ussdapp.controller;

import com.rancard.ussdapp.model.mongo.UssdMenu;
import com.rancard.ussdapp.model.request.UssdMenuRequest;
import com.rancard.ussdapp.services.UssdMenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ussd/menu")
public class MenuController {

    private final UssdMenuService ussdMenuService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addUssdMenu(@RequestBody UssdMenuRequest ussdMenuRequest , HttpServletRequest request){
        String sessionId = request.getSession().getId();
        log.info("[{}] about to add menu with request details : {}", sessionId, ussdMenuRequest.toString());
        UssdMenu menu = ussdMenuService.addMenu(ussdMenuRequest,sessionId);
        return ResponseEntity.ok().body(menu);
    }

}
