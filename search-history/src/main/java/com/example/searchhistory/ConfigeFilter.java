package com.example.searchhistory;

import jakarta.servlet.Filter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class ConfigeFilter {

    @Bean
    public Filter cookieFilter(SearchUserRepository repository){
        return (request, response, chain) -> {
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            for(Cookie cookie : Optional.ofNullable(cookies).orElse(new Cookie[]{})){
                if(cookie.getName().equals("visitor_id")){
                    chain.doFilter(request, response);
                    return;
                }
            }
            String userName = null;
            for(Cookie cookie : Optional.ofNullable(cookies).orElse(new Cookie[]{})){
                if(cookie.getName().equals("userName")){
                userName = cookie.getValue();
                }
            }
            SearchUser searchUser = Optional.ofNullable(repository.findSearchUserByUser(userName)).orElse(null);
            if(searchUser==null){
            Cookie cookie = new Cookie("visitor_id", UUID.randomUUID().toString().replace("-",""));
            ((HttpServletResponse) response).addCookie(cookie);
            SearchUser user = new SearchUser();
            user.setVisitor_id(cookie.getValue());
            user.setUser(userName);
            repository.save(user);
            chain.doFilter(request,response);
            }else {
                Cookie cookie = new Cookie("visitor_id",searchUser.getUser());
                Cookie previousCookie = new Cookie("previous_id",searchUser.getVisitor_id());
                ((HttpServletResponse) response).addCookie(cookie);
                ((HttpServletResponse) response).addCookie(previousCookie);
                chain.doFilter(request,response);
            }

        };
    }

}
