package org.example.spendwiseapi.service;


import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.model.AppUser;
import org.example.spendwiseapi.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public AppUser getUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}