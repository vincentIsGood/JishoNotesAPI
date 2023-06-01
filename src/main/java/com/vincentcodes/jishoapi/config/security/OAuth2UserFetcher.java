package com.vincentcodes.jishoapi.config.security;

import com.vincentcodes.jishoapi.config.consts.AppRoles;
import com.vincentcodes.jishoapi.entity.AppUser;
import com.vincentcodes.jishoapi.entity.AppUserDetailsWrapper;
import com.vincentcodes.jishoapi.entity.AppUserOAuth2UserWrapper;
import com.vincentcodes.jishoapi.exception.UserAlreadyExistsException;
import com.vincentcodes.jishoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * When we are granted the access token after the user accepted OAuth2,
 * we need to fetch the user's info from the provider (eg. Google).
 * Here, we are doing exactly that.
 * <p>
 * On top of that, I want to create a local user for him as well because
 * my app need to store his account info & this app's info as well.
 *
 * @see OAuth2OidcUserFetcher
 */
public class OAuth2UserFetcher implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultUserFetcher = new DefaultOAuth2UserService();

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleUserDetailsService userDetailsService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = defaultUserFetcher.loadUser(userRequest);

        // TODO: Problem here, what to do with display name? add new field to db?
        // TODO: add display name. Do not modify "name" itself
        AppUser appUser;
        try {
            appUser = createAppUserForDb(userService, userRequest.getClientRegistration(), oauth2User);
        }catch (UserAlreadyExistsException e){
            appUser = ((AppUserDetailsWrapper)userDetailsService.loadUserByUsername(
                    SSONameCodec.encodeUniqueName(userRequest.getClientRegistration(), oauth2User))).getAppUser();
        }

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        return new AppUserOAuth2UserWrapper(AppRoles.getAuthorities(), oauth2User.getAttributes(), userNameAttributeName, appUser);
    }

    public static AppUser createAppUserForDb(UserService userService,
                                             ClientRegistration clientRegistration,
                                             OAuth2User oauth2User) throws UserAlreadyExistsException {
        String uniqueUsername = SSONameCodec.encodeUniqueName(clientRegistration, oauth2User);
        return userService.createUser(uniqueUsername, "", false);
    }

}
