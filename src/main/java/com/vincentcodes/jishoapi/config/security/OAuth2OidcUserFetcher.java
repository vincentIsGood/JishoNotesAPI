package com.vincentcodes.jishoapi.config.security;

import com.vincentcodes.jishoapi.config.consts.AppRoles;
import com.vincentcodes.jishoapi.entity.AppUser;
import com.vincentcodes.jishoapi.entity.AppUserDetailsWrapper;
import com.vincentcodes.jishoapi.entity.AppUserOdicUserWrapper;
import com.vincentcodes.jishoapi.exception.UserAlreadyExistsException;
import com.vincentcodes.jishoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * Does the same thing as {@link OAuth2UserFetcher} except that it works on Oidc
 */
public class OAuth2OidcUserFetcher implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final OidcUserService defaultUserFetcher = new OidcUserService();

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleUserDetailsService userDetailsService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser odicUser = defaultUserFetcher.loadUser(userRequest);

        // TODO: Problem here, what to do with display name? add new field to db?
        // TODO: add display name. Do not modify "name" itself
        AppUser appUser;
        try {
            appUser = OAuth2UserFetcher.createAppUserForDb(userService, userRequest.getClientRegistration(), odicUser);
        }catch (UserAlreadyExistsException e){
            appUser = ((AppUserDetailsWrapper)userDetailsService.loadUserByUsername(
                    SSONameCodec.encodeUniqueName(userRequest.getClientRegistration(), odicUser))).getAppUser();
        }

        return new AppUserOdicUserWrapper(AppRoles.getAuthorities(), odicUser.getIdToken(), odicUser.getUserInfo(), appUser);
    }
}
