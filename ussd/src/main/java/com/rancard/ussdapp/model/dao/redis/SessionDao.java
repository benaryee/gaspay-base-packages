package com.rancard.ussdapp.model.dao.redis;


import com.rancard.ussdapp.model.redis.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionDao extends CrudRepository<Session, String> {

    String ping();

}
