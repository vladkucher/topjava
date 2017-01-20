package ru.javawebinar.topjava.repository.jdbc;



import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Configuration
@Profile({Profiles.JDBC, Profiles.HSQLDB})
public class JdbcHSQLDBMealRepository extends JdbcMealRepositoryImpl {
    public JdbcHSQLDBMealRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Timestamp convert(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }

}
