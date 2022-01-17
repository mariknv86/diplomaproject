package ru.mariknv86.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mariknv86.blog.model.CaptchaCode;

public interface CaptchaRepository extends JpaRepository<CaptchaCode, Long> {

    @Query("SELECT c.code FROM CaptchaCode c WHERE c.secretCode = :secret")
    String findCaptchaCodeBySecret(@Param("secret") String secret);

}
