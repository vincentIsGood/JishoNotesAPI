package com.vincentcodes.jishoapi.entity;

import com.vincentcodes.jishoapi.config.security.SSONameCodec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class AppUserOAuth2UserWrapper extends DefaultOAuth2User implements AppUserObtainable {
    private final AppUser appUser;
    private final SSONameCodec.CompositeName userInfo;

    public AppUserOAuth2UserWrapper(Collection<? extends GrantedAuthority> authorities,
                                    Map<String, Object> attributes,
                                    String nameAttributeKey, AppUser appUser) {
        super(authorities, attributes, nameAttributeKey);
        this.appUser = appUser;
        this.userInfo = SSONameCodec.decode(appUser.getName());
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public String getProviderUniqueId(){
        return userInfo.uniqueId;
    }

    /**
     * @return eg. google
     */
    public String getUserProvider(){
        return userInfo.provider;
    }
}
