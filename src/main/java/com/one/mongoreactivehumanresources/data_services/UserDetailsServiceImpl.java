package com.one.mongoreactivehumanresources.data_services;

import com.one.mongoreactivehumanresources.documents.Role;
import com.one.mongoreactivehumanresources.documents.User;
import com.one.mongoreactivehumanresources.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Qualifier("miw.users")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String mobile) {
        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new UsernameNotFoundException("mobile not found. " + mobile));
        return this.userBuilder(user.getMobile(), user.getPassword(), new Role[]{Role.AUTHENTICATED}, user.isActive());
    }

    private org.springframework.security.core.userdetails.User userBuilder(String mobile, String password, Role[] roles, boolean active) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.roleName()));
        }
        return new org.springframework.security.core.userdetails.User(mobile, password, active, true, true, true, authorities);
    }
}
