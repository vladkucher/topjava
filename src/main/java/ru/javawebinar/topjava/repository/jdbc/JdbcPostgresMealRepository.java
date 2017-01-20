package ru.javawebinar.topjava.repository.jdbc;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
@Profile({Profiles.JDBC, Profiles.POSTGRES})
public class JdbcPostgresMealRepository extends JdbcMealRepositoryImpl {
    public JdbcPostgresMealRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public LocalDateTime convert(LocalDateTime ldt) {
        return ldt;
    }
}
