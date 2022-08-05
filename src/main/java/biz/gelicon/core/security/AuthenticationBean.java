package biz.gelicon.core.security;

import biz.gelicon.core.components.core.proguser.Proguser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationBean {

    @Autowired
    protected AuthenticationManager authenticationManager;

    public Proguser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) authentication.getPrincipal()).getProgUser();
    }

    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    /**
     * Установление соединения программным способом от имени пользователя user
     * @param user -  пользователь
     * @param token - токен доступа
     * @return
     */
    public Authentication authenticate(UserDetails user, String token) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.getUsername(), token, user.getAuthorities());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

}
