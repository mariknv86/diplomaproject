package ru.mariknv86.blog.service;

import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.model.User;
import ru.mariknv86.blog.model.enums.Role;
import ru.mariknv86.blog.repository.UserRepository;
import ru.mariknv86.blog.security.SecurityUser;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final String USER_NOT_FOUND_MSG = "User not found: ";
    private final String MODERATOR_NOT_DEFINED_MSG = "Moderator not defined";
    private final String USER_SESSION_NOT_FOUND_MSG = "User session not found";


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG + email));
        return SecurityUser.fromUser(user);
    }

    public User getUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG + email));
        return user;
    }

    public User getCurrentUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user instanceof UserDetails) {
            return getUserByUsername(((UserDetails) user).getUsername());
        } else {
            throw new AuthenticationCredentialsNotFoundException(USER_SESSION_NOT_FOUND_MSG);
        }
    }

    public User getModerator() {
        return userRepository.findByIsModerator((byte) 1)
            .orElseThrow(() -> new EntityNotFoundException(MODERATOR_NOT_DEFINED_MSG));
    }

    public Boolean isUserModerator() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(
            Role.MODERATOR);
    }


}
