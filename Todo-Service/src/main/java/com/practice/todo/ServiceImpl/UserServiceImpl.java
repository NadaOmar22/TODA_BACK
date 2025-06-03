package com.practice.todo.ServiceImpl;

import com.practice.todo.dto.UserDTO;
import com.practice.todo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;


    @Override
    public UserDTO getUserById(String userId){
        String url = "http://localhost:8080/users/" + userId;
        return restTemplate.getForObject(url, UserDTO.class);
    }



}
