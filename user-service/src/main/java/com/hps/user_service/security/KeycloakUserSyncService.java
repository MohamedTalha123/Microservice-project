//package com.hps.user_service.security;
//
//import com.hps.user_service.entity.User;
//import com.hps.user_service.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class KeycloakUserSyncService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public KeycloakUserSyncService(@Lazy RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Scheduled(fixedRate = 60000)
//    public void syncUsersFromKeycloak() {
//        String keycloakUrl = "http://localhost:9000/realms/Hps-microservice/protocol/openid-connect/token";
//        String clientId = "hps-back-end";
//        String clientSecret = "JlzSi7645HNmrRnmGfYlajBVqsEbo8ua";
//        String grantType = "client_credentials";
//
//        // Create the request body
//        MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<>();
//        tokenRequest.add("client_id", clientId);
//        tokenRequest.add("client_secret", clientSecret);
//        tokenRequest.add("grant_type", grantType);
//
//        // Add headers for form-encoded request
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
//
//        // Create the request entity
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(tokenRequest, headers);
//
//        // Retrieve the access token
//        Map<String, Object> response = restTemplate.postForObject(keycloakUrl, request, Map.class);
//        String accessToken = (String) response.get("access_token");
//
//        // Get users from Keycloak
//        String usersUrl = "http://localhost:9000/admin/realms/Hps-microservice/users";
//        HttpHeaders authHeaders = new HttpHeaders();
//        authHeaders.setBearerAuth(accessToken);
//
//        HttpEntity<Void> authRequest = new HttpEntity<>(authHeaders);
//
//        try {
//            ResponseEntity<List> keycloakUsersResponse = restTemplate.exchange(usersUrl, HttpMethod.GET, authRequest, List.class);
//            List<Map<String, Object>> keycloakUsers = keycloakUsersResponse.getBody();
//
//            // Sync users with the database
//            for (Map<String, Object> keycloakUser : keycloakUsers) {
//                String userId = (String) keycloakUser.get("id");
//                String username = (String) keycloakUser.get("username");
//                String email = (String) keycloakUser.get("email");
//                String firstName = (String) keycloakUser.get("firstName");
//                String lastName = (String) keycloakUser.get("lastName");
//
//                // Fetch user's custom attributes, including phone
//                Map<String, List<String>> attributes = (Map<String, List<String>>) keycloakUser.get("attributes");
//                String phone = attributes != null && attributes.containsKey("phone") ? attributes.get("phone").get(0) : null;
//
//                // Fetch user's roles
//                String rolesUrl = "http://localhost:9000/admin/realms/Hps-microservice/users/" + userId + "/role-mappings/realm";
//                ResponseEntity<List> rolesResponse = restTemplate.exchange(rolesUrl, HttpMethod.GET, authRequest, List.class);
//                List<Map<String, Object>> roles = rolesResponse.getBody();
//                StringBuilder rolesBuilder = new StringBuilder();
//
//                if (roles != null && !roles.isEmpty()) {
//                    for (Map<String, Object> role : roles) {
//                        String roleName = (String) role.get("name");
//                        if ("user".equals(roleName) || "admin".equals(roleName)) {
//                            rolesBuilder.append(roleName).append(",");
//                        }
//                    }
//                }
//
//                // Remove the trailing comma
//                String rolesString = rolesBuilder.length() > 0 ? rolesBuilder.substring(0, rolesBuilder.length() - 1) : "";
//
//                User user = userRepository.findByEmail(email).orElse(new User());
//                user.setUserName(username);
//                user.setEmail(email);
//                user.setPhone(phone);  // Set the phone number
//                user.setFirstName(firstName);
//                user.setLastName(lastName);
//
//                user.setRole(rolesString);
//
//                userRepository.save(user);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
