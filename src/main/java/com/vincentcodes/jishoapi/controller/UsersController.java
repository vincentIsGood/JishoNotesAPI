package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.config.security.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/users")
public class UsersController {
    @Autowired
    AuthenticationContext authContext;

    @GetMapping("/loginstatus")
    public ResponseEntity<Void> amILoggedIn(){
        Authentication authentication = authContext.getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken || !authentication.isAuthenticated())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(null);
    }
}
