package com.rancard.ussdapp.services.sessionmanagement;


import com.rancard.ussdapp.model.dao.redis.SessionDao;
import com.rancard.ussdapp.model.redis.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SessionManager {
    private final SessionDao sessionDao;

    public SessionManager(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public Session getSessionById(String id){
        return sessionDao.findById(id).orElse(null);
    }

    public void persistSession(Session session, String sessionId){
        log.info("[" + sessionId + "] about to save new session details to redis");

        if(session != null){
            sessionDao.save(session);
            log.info("["+sessionId+"] done saving session details");
            return;
        }
        log.info("["+sessionId+"] cannot save empty session details");
    }

    public Session deleteSession(Session session, String sessionId){
        log.info("[" + sessionId + "] --- about to delete existing session details");

        if(session != null){
            sessionDao.delete(session);
            log.info("["+sessionId+"] done deleting session details");
        }
        return session;
    }

    public long pingServer(){
         return sessionDao.count();
    }
}
