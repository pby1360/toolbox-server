package com.toolbox.toolboxserver.configuration;

import com.toolbox.toolboxserver.domain.auth.entity.User;
import com.toolbox.toolboxserver.domain.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository repository;

    public UserDetailServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        CustomUserDetails detail = new CustomUserDetails(user.getEmail(), user.getName(), null);
        return detail;
    }
}
