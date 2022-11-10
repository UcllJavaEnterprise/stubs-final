package be.ucll.java.ent.security;

import be.ucll.java.ent.controller.UserController;
import be.ucll.java.ent.domain.Role;
import be.ucll.java.ent.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger log = Logger.getLogger(MyUserDetailsService.class.getName());

    @Autowired
    private UserController uCtrl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Access requested for user with loginid: " + username);

        User user = uCtrl.getUserByLoginId(username);
        if (user == null) {
            log.warning("User not found in back-end. Access denied");
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        log.info("User successfully authenticated");
        return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(), authorities);
    }

}

