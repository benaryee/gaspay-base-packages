package com.rancard.apigateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebFluxSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
//        serverHttpSecurity
//                .csrf().disable()
//                .authorizeExchange(exchange ->
//                        exchange.pathMatchers("/api/ussd/menu","/eureka/**", "/api/payment/**" ,
//                                        "/api/ussd", "/api/product/**", "/api/payment/topup/callback", "/api/auth/**")
//                                .permitAll()
//                                .anyExchange()
//                                .authenticated())
//                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
//        return serverHttpSecurity.build();
//    }
}
