package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.config.consts.SecurityRelatedConsts;
import com.vincentcodes.jishoapi.config.security.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/jishonotes")
public class OAuth2UserInterface {
    @Autowired
    private AuthenticationContext authContext;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    // we'll store the url into a cookie
    @GetMapping("/login/oauth2")
    public void redirectOauth2LoginProvider(HttpServletRequest req, HttpServletResponse res,
                                            @RequestParam("prov") String provider,
                                            @RequestParam("ret") String returnUrl) throws IOException {
        Authentication authentication = authContext.getAuthentication();
        boolean isLoggedIn = !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
        if(isLoggedIn) return;

        CorsConfiguration catchAllCorsConfig = ((UrlBasedCorsConfigurationSource)corsConfigurationSource).getCorsConfigurations().get("/**");
        if(catchAllCorsConfig.checkOrigin(returnUrl) == null) {
            res.sendError(400, "Invalid return url");
            return;
        }

        String registrationId;
        switch(provider.toLowerCase()){
            case "github": registrationId = "github"; break;
            case "google": registrationId = "google"; break;
            default: res.sendError(400, "Invalid provider string"); return;
        }
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(300); // 5min
        session.setAttribute("ret", returnUrl);
        res.sendRedirect("/oauth2/authorization/" + registrationId);
    }

    // redirect user back to where he wants
    @RequestMapping("/oauth2/success/redirect")
    public RedirectView oauth2AuthSuccess(HttpSession session) throws IOException {
        session.setMaxInactiveInterval(SecurityRelatedConsts.SESSION_MAX_EXPIRE_INTERVAL);
        return new RedirectView(session.getAttribute("ret").toString());
    }
}
