package ru.mariknv86.blog.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mariknv86.blog.model.GlobalSettings;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {

    List<GlobalSettings> findAll();

    @Modifying
    @Transactional
    @Query(value = "UPDATE global_settings SET value = :value WHERE code = :code", nativeQuery = true)
    void updateValue(@Param("value") String value, @Param("code") String code);



}
