package ru.mariknv86.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitResponseDto {

    private String title = "DevPub";
    private String subtitle = "Рассказы разработчиков";
    private String phone = "+7 999 666-44-55";
    private String email = "mail@mail.ru";
    private String copyright = "Марат Калимуллин";
    private String copyrightFrom = "2021";

}
