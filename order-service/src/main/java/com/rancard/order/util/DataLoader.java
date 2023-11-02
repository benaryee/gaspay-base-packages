package com.rancard.order.util;

import com.rancard.order.model.mongo.Agent;
import com.rancard.order.model.mongo.Role;
import com.rancard.order.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AgentRepository agentRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}
