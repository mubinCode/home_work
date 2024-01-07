package com.example.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyService {
    @Autowired
    private UserRepo repo;
    @Autowired
    private WebCall webCall;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse res;

    public String home() {
        return "Home123";
    }

    public String add(User user) {
        repo.save(user);
        Cookie[] cookies = request.getCookies();
        List<String> cookieList = Arrays.stream(Optional.ofNullable(cookies).orElse(new Cookie[]{})).map(cookie1 -> cookie1.getName() + "=" + cookie1.getValue()).collect(Collectors.toList());
        String cookiesString = String.join(";", cookieList);
        ResponseEntity<String> response = webCall.call(cookiesString, user.getName());
        String responseCookie = response.getHeaders().getFirst("Set-Cookie");
        if (responseCookie == null) {
            System.out.println("");
        } else {
            for (String str : response.getHeaders().get("Set-Cookie")) {
                String[] parts = str.split("=");
                String cookieName = parts[0];
                String cookieValue = parts[1];
                Cookie cookie = new Cookie(cookieName, cookieValue);
                res.addCookie(cookie);
            }
        }
//        response.getHeaders().getCookie

        return "User created success";
    }

    public String signIn(User user) {

        try {
            User getUser = repo.findUserByName(user.getName());
            if(getUser.getPassword().equals(user.getPassword())){
                Cookie[] cookies = request.getCookies();
                List<String> cookieList = Arrays.stream(Optional.ofNullable(cookies).orElse(new Cookie[]{})).map(cookie1 -> cookie1.getName() + "=" + cookie1.getValue()).collect(Collectors.toList());
                String cookiesString = String.join(";", cookieList);
                ResponseEntity<String> response = webCall.call(cookiesString, user.getName());
                String responseCookie = response.getHeaders().getFirst("Set-Cookie");
                if (responseCookie == null) {
                    System.out.println("");
                } else {
                    for (String str : response.getHeaders().get("Set-Cookie")) {
                        String[] parts = str.split("=");
                        String cookieName = parts[0];
                        String cookieValue = parts[1];
                        Cookie cookie = new Cookie(cookieName, cookieValue);
                        res.addCookie(cookie);
                    }
                }
            return "success.";
            }else {
                return "Credentials are not matched. Try with correct credentials.";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
