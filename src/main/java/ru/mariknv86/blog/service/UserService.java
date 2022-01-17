package ru.mariknv86.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.model.User;
import ru.mariknv86.blog.repository.UserRepository;
import ru.mariknv86.blog.security.SecurityUser;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return SecurityUser.fromUser(user);
    }

    public User getUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return user;
    }

    public User getCurrentUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user instanceof UserDetails) {
            return getUserByUsername(((UserDetails) user).getUsername());
        } else {
            throw new AuthenticationCredentialsNotFoundException("User session not found");
        }
    }




}
