package ru.mariknv86.blog.controller;

import java.security.Principal;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mariknv86.blog.dto.request.LoginRequestDto;
import ru.mariknv86.blog.dto.request.UserRequestDto;
import ru.mariknv86.blog.dto.response.CaptchaResponseDto;
import ru.mariknv86.blog.dto.response.LoginResponseDto;
import ru.mariknv86.blog.dto.response.ResultsResponseDto;
import ru.mariknv86.blog.dto.response.UserLoginResponse;
import ru.mariknv86.blog.repository.PostRepository;
import ru.mariknv86.blog.repository.UserRepository;
import ru.mariknv86.blog.service.AuthService;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class ApiAuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/check")
    public ResponseEntity<?> check(Principal principal) {
        if(principal == null) {
            return ResponseEntity.ok(new LoginResponseDto());
        }
        return ResponseEntity.ok(getLoginResponse(principal.getName()));
    }

    @GetMapping("/captcha")
    @ResponseStatus(HttpStatus.OK)
    public CaptchaResponseDto genCaptcha() {
        return authService.genAndSaveCaptcha();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResultsResponseDto<?> registerNewUser(@Valid @RequestBody UserRequestDto user) {
        return authService.registerNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {

        Authentication auth = authenticationManager
            .authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(getLoginResponse(user.getUsername()));
    }

    private LoginResponseDto getLoginResponse(String email) {

        ru.mariknv86.blog.model.User currentUser = userRepository.findByEmail(email)
            .orElseThrow(() ->  new UsernameNotFoundException(email));

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setEmail(currentUser.getEmail());
        userLoginResponse.setName(currentUser.getName());
        userLoginResponse.setModeration(currentUser.getIsModerator() == 1);
        userLoginResponse.setModerationCount(postRepository.getNotModeratedPostsCount());
        userLoginResponse.setId(currentUser.getId());


        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setResult(true);
        loginResponseDto.setUserLoginResponse(userLoginResponse);

        return loginResponseDto;

    }

}
