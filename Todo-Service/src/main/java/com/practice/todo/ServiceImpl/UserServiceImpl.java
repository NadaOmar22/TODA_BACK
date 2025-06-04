package com.practice.todo.ServiceImpl;

import com.practice.todo.dto.UserDTO;
import com.practice.todo.security.AuthConstants;
import com.practice.todo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final AuthConstants authConstants;

    @Override
    public UserDTO getUserById(String userId) {
        String url = "http://localhost:8080/users/" + userId;

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        System.out.println("authConstants.AUTH_TOKEN " + authConstants.AUTH_TOKEN);
        headers.setBearerAuth(authConstants.AUTH_TOKEN);

        // Create request entity with headers
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Perform exchange
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                UserDTO.class
        );
        System.out.println(response.getBody().toString());
        return response.getBody();
    }




}