package com.vincentcodes.jishoapi.service;

import com.vincentcodes.jishoapi.config.security.AuthenticationContext;
import com.vincentcodes.jishoapi.entity.AppUser;
import com.vincentcodes.jishoapi.entity.AppUserDetailsWrapper;
import com.vincentcodes.jishoapi.exception.InvalidOperation;
import com.vincentcodes.jishoapi.exception.UserAlreadyExistsException;
import com.vincentcodes.jishoapi.exception.UserNotFoundException;
import com.vincentcodes.jishoapi.repository.AppUserCrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Note: Authentication service is not performed here. This user service
 * is responsible for getting, creating and updating users.
 * @see com.vincentcodes.jishoapi.config.WebSecurityConfig
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private AppUserCrudDao userDao;

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Autowired
    //private SimpleUserDetailsService userDetailsService;

    /**
     * <p>
     * In short, a person can only modify info for himself. Same person?
     * <p>
     * I do not want people to change anyone else's accounts info by brute
     * forcing UUIDs.
     * <p>
     * To do that, I check its authorized session (clientAuth) against what
     * the person want to modify (userId).
     * @param userId uid to check against
     * @return same person or not
     */
    // TODO: make verification into a class(?)
    private boolean verifyIdentity(UUID userId){
        Authentication clientAuth = authenticationContext.getAuthentication();
        AppUserDetailsWrapper details = (AppUserDetailsWrapper) clientAuth.getPrincipal();
        return details.getAppUser().getUserId().equals(userId);
    }

    // TODO: these functions, we don't need to take UUID from requests if we already have a session
    /**
     * Get himself
     */
    public AppUser getUser(UUID uuid){
        if(verifyIdentity(uuid))
            throw new InvalidOperation(uuid + " (userId) is getting another person's info");
        Optional<AppUser> realUserOptional = userDao.findById(uuid);
        if(realUserOptional.isEmpty())
            throw new UserNotFoundException(uuid.toString());
        return realUserOptional.get();
    }

    /**
     * @return user with UUID
     */
    public AppUser createUser(String username, String password){
        return createUser(username, password, true);
    }
    public AppUser createUser(String username, String password, boolean allowUserPassLogin){
        if(allowUserPassLogin && username.contains("\\"))
            throw new BadCredentialsException("Username cannot contain invalid character '\\'");
        Optional<AppUser> realUserOptional = userDao.findByName(username);
        if(realUserOptional.isPresent())
            throw new UserAlreadyExistsException(username);
        AppUser appUser = new AppUser(username, passwordEncoder.encode(password));
        appUser.setAllowUserPassLogin(allowUserPassLogin);
        return userDao.save(appUser);
    }

    /**
     * @return whether database is updated with the new info
     */
    public boolean updateUser(AppUser clientInfo, String newName, String newPassword){
        if(verifyIdentity(clientInfo.getUserId()))
            throw new InvalidOperation(clientInfo.getUserId() + " (userId) is modifying another person's info");
        Optional<AppUser> realUserOptional = userDao.findById(clientInfo.getUserId());
        if(realUserOptional.isEmpty())
            throw new UserNotFoundException(clientInfo.getUserId().toString());

        AppUser realUser = realUserOptional.get();
        if(newName == null && newPassword == null)
            return false;
        if(newName != null) clientInfo.setName(newName);
        if(newPassword != null) clientInfo.setPass(newPassword);
        userDao.save(clientInfo);
        return true;
    }

    /**
     * @return whether the user is successfully deleted
     */
    public boolean deleteUser(AppUser clientInfo){
        if(verifyIdentity(clientInfo.getUserId()))
            throw new InvalidOperation(clientInfo.getUserId() + " (userId) is deleting another person's account");
        userDao.deleteById(clientInfo.getUserId());
        return true;
    }


    //public void renewSession(HttpServletRequest request){
    //    HttpSession session = request.getSession(false);
    //    System.out.println(session);
    //    if(session != null)
    //        invalidateSession(request);
    //    session = request.getSession(true);
    //    session.setAttribute("auth", true);
    //}
    //
    //public void invalidateSession(HttpServletRequest request){
    //    HttpSession session = request.getSession(false);
    //    SecurityContextHolder.clearContext();
    //    if(session != null) {
    //        session.invalidate();
    //    }
    //}
}
