package com.example.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebCall {

    private final WebClient webClient = WebClient.create();

    public ResponseEntity<String> call(String cookies, String userName){
        return webClient.get()
                .uri("http://localhost:8080/home")
                .accept(MediaType.APPLICATION_JSON)
                .header("Cookie", cookies+";"+"userName="+userName)
                .retrieve()
                .toEntity(String.class)
                .block();

    }
}
