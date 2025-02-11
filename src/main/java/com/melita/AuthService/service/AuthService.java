package com.melita.AuthService.service;

import com.melita.AuthService.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public String login(String username, String password);
    public User register(User user);
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
