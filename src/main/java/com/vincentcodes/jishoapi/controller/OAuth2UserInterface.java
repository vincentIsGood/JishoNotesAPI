package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.config.consts.ApiEndpoints;
import com.vincentcodes.jishoapi.config.consts.SecurityRelatedConsts;
import com.vincentcodes.jishoapi.config.security.AuthenticationContext;
import com.vincentcodes.jishoapi.utils.UriExtendedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;

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
        if(catchAllCorsConfig.checkOrigin(UriExtendedUtils.getOrigin(returnUrl)) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid return url");

        String registrationId;
        switch(provider.toLowerCase()){
            case "github": registrationId = "github"; break;
            case "google": registrationId = "google"; break;
            default: throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid provider string");
        }
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(300); // 5min
        session.setAttribute("ret", returnUrl);
        res.setStatus(HttpServletResponse.SC_FOUND);
        res.setHeader("Location", ApiEndpoints.OAUTH2_INTERNAL + "/" + registrationId);
    }

    // redirect user back to where he wants
    @RequestMapping("/oauth2/success/redirect")
    public RedirectView oauth2AuthSuccess(HttpSession session) {
        session.setMaxInactiveInterval(SecurityRelatedConsts.SESSION_MAX_EXPIRE_INTERVAL);

        Object retUrlObj = session.getAttribute("ret");
        if(retUrlObj == null)
            return new RedirectView("/");
        return new RedirectView(retUrlObj.toString());
    }
}
