package com.gaspay.ussdapp.controller.handler;


import com.gaspay.ussdapp.model.payload.DispatchObject;
import com.gaspay.ussdapp.model.redis.Session;
import com.gaspay.ussdapp.model.request.UssdRequest;
import com.gaspay.ussdapp.model.response.UssdResponse;
import com.gaspay.ussdapp.services.RequestDispatcher;
import com.gaspay.ussdapp.services.sessionmanagement.SessionManager;
import com.gaspay.ussdapp.utils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.gaspay.ussdapp.model.enums.MenuLevel.START;

@Slf4j
@RequestMapping("/api/ussd")
@RestController
@RequiredArgsConstructor
public class RequestController {


    protected String INVALID_REQUEST = "Invalid Request";

    private final RequestDispatcher requestDispatcherService;
    private final SessionManager sessionManager;
    private final DateUtils dateUtils;


    @PostMapping
    public String processUssdRequest(@RequestBody UssdRequest ussdRequest, HttpServletRequest request) {
        long start = new Date().getTime();
        long end;

        requestDispatcherService.setResponse(new UssdResponse());

        String redisDate = dateUtils.formatRedisDate(new Date());
        DispatchObject dispatchObject = new DispatchObject();

        String sessionId = request.getSession().getId();
        log.info("[" + sessionId + "] about to validate request ::: " + ussdRequest.toString());

        UssdResponse response = new UssdResponse();

        boolean isValidRequest = ussdRequest.isValidRequest(sessionId);

        log.info("[" + sessionId + "] formatted msisdn request ::: " + ussdRequest);

        if (isValidRequest) {
            log.info(" [ " + sessionId + " ] Request" + request.getQueryString());
            dispatchObject.setUssdRequest(ussdRequest);
            String ussdSessionId = redisDate+":"+ussdRequest.getMsisdn()+":"+ussdRequest.getSessionId() + "@" + ussdRequest.getMobileNetwork();

            log.info("[" + sessionId + "] checking existing sessions for : " + ussdSessionId);

            Session currentSession = sessionManager.getSessionById(ussdSessionId);

            if (ussdRequest.getMenu().equals("0")) {
                currentSession = new Session(ussdSessionId, START);
                currentSession.setInitialRequest(true);
                dispatchObject.setSession(currentSession);
                response = requestDispatcherService.handleInitialRequest(dispatchObject,request,sessionId);

                response.setRequestId(sessionId);
                end = new Date().getTime();

            } else {
                currentSession.setInitialRequest(false);

                log.info("Retrieving old session menu for session id :: " + currentSession.getId());

                dispatchObject.setSession(currentSession);
                response = requestDispatcherService.processRequest(dispatchObject,request,sessionId);
                end = new Date().getTime();
                response.setRequestId(sessionId);
            }
            response.setDuration(end - start);
            log.info("["+sessionId+"] current session state ::: "+ currentSession);
            sessionManager.persistSession(currentSession, sessionId);
        } else {
            end = new Date().getTime();
            response.setContinueSession(false);
            response.setDuration(end - start);
            response.setRequestId(sessionId);
            response.setMessage(INVALID_REQUEST);
        }
        log.info("["+sessionId+"] user "+ ussdRequest.getMsisdn()+ " response : " + response);
        dispatchObject = null;
        return response.toUnifierResponse(ussdRequest);
    }

}
