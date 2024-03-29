package com.vincentcodes.jishoapi.config.security;

import com.vincentcodes.jishoapi.config.consts.AppRoles;
import com.vincentcodes.jishoapi.entity.AppUser;
import com.vincentcodes.jishoapi.entity.AppUserDetailsWrapper;
import com.vincentcodes.jishoapi.repository.AppUserCrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Users here will not expire or disabled. And, we have ONE role only.
 *
 * @see org.springframework.security.core.userdetails.User
 * @link https://www.baeldung.com/spring-security-authentication-with-a-database
 * @link https://github.com/eugenp/tutorials/tree/master/spring-security-modules/spring-security-web-boot-1
 */
@Transactional
public class SimpleUserDetailsService implements UserDetailsService {
    @Autowired
    private AppUserCrudDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalUser = userDao.findByName(username);
        if(optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        AppUser user = optionalUser.get();
        return new AppUserDetailsWrapper(user.getName(), user.getPass(), AppRoles.getAuthorities(), user);
    }
}
