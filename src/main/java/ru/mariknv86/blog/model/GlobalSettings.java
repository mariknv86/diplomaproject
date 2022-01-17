package ru.mariknv86.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mariknv86.blog.model.enums.GlobalSetting;

@Data
@Entity
@Table(name = "global_settings")
@AllArgsConstructor
@NoArgsConstructor
public class GlobalSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GlobalSetting code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

}
