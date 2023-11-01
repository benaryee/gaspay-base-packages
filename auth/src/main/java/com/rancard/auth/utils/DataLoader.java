package com.rancard.auth.utils;

import com.rancard.auth.model.RoleRepository;
import com.rancard.auth.model.mongo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() < 1) {
            roleRepository.saveAll(List.of(
                    new Role(100, "USER", "User Role"),
                    new Role(200,"VENDOR", "Vendor Role"),
                    new Role(300, "AGENT", "Agent Role")
            ));

        }
    }
}
