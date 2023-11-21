package com.gaspay.order.util;

import com.gaspay.order.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AgentRepository agentRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}
