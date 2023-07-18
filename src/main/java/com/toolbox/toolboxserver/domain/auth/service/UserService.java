package com.toolbox.toolboxserver.domain.auth.service;

import com.toolbox.toolboxserver.domain.auth.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    boolean isExist(String email);
    User join(User user);
    User getUserByEmail(String email);
}
