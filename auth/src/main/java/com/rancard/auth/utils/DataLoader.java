package com.rancard.auth.utils;

import com.rancard.auth.model.AgentRepository;
import com.rancard.auth.model.RoleRepository;
import com.rancard.auth.model.mongo.Agent;
import com.rancard.auth.model.mongo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AgentRepository agentRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() < 1) {
            roleRepository.saveAll(List.of(
                    new Role(100, "USER", "User Role"),
                    new Role(200,"VENDOR", "Vendor Role"),
                    new Role(300, "AGENT", "Agent Role")
            ));

        }

        if (agentRepository.count() < 1) {

            Role role  = Role.builder()
                    .code(300)
                    .name("AGENT")
                    .description("Agent")
                    .build();
            HashSet<Role> roles = new HashSet<>();
            roles.add(role);

            Agent agent = Agent.builder()
                    .firstname("Nii")
                    .lastname("Ayi")
                    .msisdn("233548410151")
                    .outletName("Rancard")
                    .active(true)
                    .roles(roles)
                    .build();
            agentRepository.save(agent);
        }
    }
}
