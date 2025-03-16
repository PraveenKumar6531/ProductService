package org.praveen.productservice.commons;

import org.praveen.productservice.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommons {
    private RestTemplate restTemplate;
    public AuthCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String tokenValue){
        ResponseEntity<UserDto> responseEntity = restTemplate.getForEntity("http://localhost:4141/users/validate/"+tokenValue, UserDto.class);
        if(responseEntity.getBody() == null){
            return null;
        }
        return responseEntity.getBody();
    }
}
