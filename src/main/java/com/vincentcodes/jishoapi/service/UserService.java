package com.vincentcodes.jishoapi.service;

import com.vincentcodes.jishoapi.entity.AppUser;
import com.vincentcodes.jishoapi.repository.AppUserDao;
import com.vincentcodes.jishoapi.utils.SimpleListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private AppUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @return successful or not
     */
    public boolean validateUser(String username, String rawPass){
        Optional<AppUser> user = userDao.findByName(username);
        return user.isPresent() && passwordEncoder.matches(rawPass, user.get().getPass());
    }

    public void renewSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        System.out.println(session);
        if(session != null)
            invalidateSession(request);
        session = request.getSession(true);
        session.setAttribute("auth", true);
    }

    public void invalidateSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if(session != null) {
            session.invalidate();
        }
    }
}
