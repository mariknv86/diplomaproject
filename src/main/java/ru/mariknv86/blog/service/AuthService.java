package ru.mariknv86.blog.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mariknv86.blog.dto.request.UserRequestDto;
import ru.mariknv86.blog.dto.response.CaptchaResponseDto;
import ru.mariknv86.blog.dto.response.ResultsResponseDto;
import ru.mariknv86.blog.exception.InvalidAttributeException;
import ru.mariknv86.blog.exception.InvalidCaptchaException;
import ru.mariknv86.blog.mapper.CaptchaToCaptchaDto;
import ru.mariknv86.blog.mapper.UserDtoToUser;
import ru.mariknv86.blog.model.CaptchaCode;
import ru.mariknv86.blog.model.User;
import ru.mariknv86.blog.repository.CaptchaRepository;
import ru.mariknv86.blog.repository.UserRepository;
import ru.mariknv86.blog.utils.RandomStringGenerator;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class AuthService {

    private static final String DATA_PREFIX = "data:image/png;base64,";
    private static final String DEFAULT_AVATAR = "/default-1.png";

    private static final int MIN_PASSWORD_LENGTH = 6;

    private CaptchaRepository captchaRepository;
    private UserRepository userRepository;
    private UserDtoToUser userDtoToUser;
    private PasswordEncoder passwordEncoder;
    private CaptchaToCaptchaDto captchaToCaptchaDto;

    public ResultsResponseDto<?> registerNewUser(UserRequestDto dto) {
        String captchaCode = captchaRepository.findCaptchaCodeBySecret(dto.getCaptchaSecret());

        if(captchaCode == null) {
            throw new InvalidAttributeException(Map.of("captcha", "Код с картинки введён неверно"));
        } else {
            validateCaptcha(dto.getCaptcha(), captchaCode);
        }

        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new InvalidAttributeException(Map.of("email", "Этот e-mail уже зарегистрирован"));
        }

        if(dto.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidAttributeException(Map.of("password", "Пароль короче 6-ти символов"));
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = userDtoToUser.map(dto);

        if(dto.getName() != null && !dto.getName().isEmpty()) {
            user.setName(dto.getName());
        } else {
            user.setName(dto.getEmail());
        }

        user.setPhoto(DEFAULT_AVATAR);
        userRepository.save(user);
        return new ResultsResponseDto<>()
            .setResult(true);
    }

    private void validateCaptcha(String entered, String actual) {
        if(!entered.equals(actual)) {
            throw new InvalidCaptchaException("Код с картинки введён неверно");
        }

    }

    public CaptchaResponseDto genAndSaveCaptcha() {

        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setWidth(100);
        cs.setFontFactory(new RandomFontFactory(30, new String[] {"Verdana"}));
        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));

        CaptchaCode captchaCode;

        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            String plainCode = EncoderHelper.getChallangeAndWriteImage(
                cs, "png", byteArrayOutputStream);
            String encodedCode = DATA_PREFIX + Base64.getEncoder()
                .encodeToString(byteArrayOutputStream.toByteArray());
            captchaCode =
                new CaptchaCode()
                    .setCode(plainCode)
                    .setSecretCode(RandomStringGenerator.randomString(5))
                    .setTime(LocalDateTime.now());
            captchaRepository.save(captchaCode);
            return captchaToCaptchaDto.map(captchaCode).setImage(encodedCode);
        } catch (IOException e) {
            throw new InvalidCaptchaException("Error generating captcha");

        }
    }


}
