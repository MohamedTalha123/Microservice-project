//package com.hps.apigateway.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.web.reactive.config.CorsRegistry;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebFluxSecurity
//public class  SecurityConfig {
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
//        return  serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**"
//                                ,"/api/v1/products/public/**"
//                                ,"/api/v1/brands/public/**"
//                                ,"/api/v1/products/**"
//                                ,"/api/v1/brands/**"
//                                )
//                        .permitAll()
//                        .anyExchange().authenticated()
//                ).oauth2ResourceServer((oauth) -> oauth
//                        .jwt(withDefaults()))
//                .cors(withDefaults()
//                )
//                .build();
//    }
////    @Bean
////    WebMvcConfigurer cors(){
////        return new WebMvcConfigurer (){
////            @Override
////            public void addCorsMappings(CorsRegistry registry) {
////                registry
////                        .addMapping("/**")
////                        .allowedOrigins("http://localhost:4200")
////                        .allowedMethods("*")
////                        .allowedHeaders("*");
////            }
////        };
////    }
//}
