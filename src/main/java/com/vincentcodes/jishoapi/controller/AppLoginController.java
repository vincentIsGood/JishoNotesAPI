package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Custom login
 * @link https://stackoverflow.com/questions/63695023/how-to-do-a-restful-login-api-using-spring-security
 */
@RestController
@RequestMapping("jishonotes/v1/users")
public class AppLoginController {
    @Autowired
    private UserService userService;

    // https://stackoverflow.com/questions/37803440/invalidate-session-spring-security
    @GetMapping("/logout")
    public void logoutFromApp(HttpServletRequest request){
        userService.invalidateSession(request);
    }

    /**
     * @param reqBody [json]; "name" MUST be unique and "pass" attribute MUST NOT be encoded
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginToApp(@RequestBody Map<String, String> reqBody, HttpServletRequest request){
        String username = reqBody.get("name");
        String password = reqBody.get("pass");
        if(username == null || password == null)
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

        if(userService.validateUser(username, password)){
            userService.renewSession(request);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
}
