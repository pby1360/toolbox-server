package com.toolbox.toolboxserver.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SimpleAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if (id != null) return Optional.of(id);
        else return null;
    }
}
