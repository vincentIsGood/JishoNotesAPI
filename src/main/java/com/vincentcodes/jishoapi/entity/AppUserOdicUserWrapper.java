package com.vincentcodes.jishoapi.entity;

import com.vincentcodes.jishoapi.config.security.SSONameCodec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Collection;

public class AppUserOdicUserWrapper extends DefaultOidcUser implements AppUserObtainable{
    private final AppUser appUser;
    private final SSONameCodec.CompositeName userInfo;

    public AppUserOdicUserWrapper(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo, AppUser appUser) {
        // use default attributeKey = "sub"
        super(authorities, idToken, userInfo);
        this.appUser = appUser;
        this.userInfo = SSONameCodec.decode(appUser.getName());
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public String getUsername(){
        return userInfo.uniqueId;
    }

    /**
     * @return eg. google
     */
    public String getUserProvider(){
        return userInfo.provider;
    }
}
