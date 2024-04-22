package com.backend.blog.security;

import com.backend.blog.entity.User;
import com.backend.blog.exception.ResourceNotFoundException;
import com.backend.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private  final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loading user from database

        User user=userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","Email:"+username,0));


        return user;
    }
}
