package com.toolbox.toolboxserver.domain.auth.service.impl;

import com.toolbox.toolboxserver.domain.auth.entity.User;
import com.toolbox.toolboxserver.domain.auth.repository.UserRepository;
import com.toolbox.toolboxserver.domain.auth.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class SimpleUserService implements UserService {

    private UserRepository repository;

    public SimpleUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isExist(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User join(User user) {
        return repository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}
