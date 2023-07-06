package com.vincentcodes.jishoapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.vincentcodes.jishoapi.config.security.AuthenticationContext;
import com.vincentcodes.jishoapi.entity.AppUser;
import com.vincentcodes.jishoapi.entity.AppUserObtainable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/users")
public class UsersController {
    @Autowired
    AuthenticationContext authContext;

    @GetMapping("/loginstatus")
    public ResponseEntity<Void> amILoggedIn(){
        return authContext.isLoggedIn()? ResponseEntity.ok(null) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/info")
    @JsonView({AppUser.CENSORED.class})
    public AppUser getUserInfo(){
        if(!authContext.isLoggedIn())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not logged in");
        return ((AppUserObtainable) authContext.getAuthentication().getPrincipal()).getAppUser();
    }
}
