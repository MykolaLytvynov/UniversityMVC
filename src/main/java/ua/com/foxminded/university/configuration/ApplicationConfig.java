package ua.com.foxminded.university.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.com.foxminded.university")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Value("${postgres.driver}")
    private String driverForPostgres;

    @Value("${postgres.url}")
    private String urlForPostgres;

    @Value("${postgres.username}")
    private String userNameForPostgres;

    @Value("${postgres.password}")
    private String passwordForPostgres;


    @Value("${h2.driver}")
    private String driverForH2;

    @Value("${h2.url}")
    private String urlForH2;

    @Value("${h2.username}")
    private String userNameForH2;

    @Value("${h2.password}")
    private String passwordForH2;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driverForPostgres);
        dataSource.setUrl(urlForPostgres);
        dataSource.setUsername(userNameForPostgres);
        dataSource.setPassword(passwordForPostgres);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }


    @Bean
    public DataSource dataSourceForTest() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driverForH2);
        dataSource.setUrl(urlForH2);
        dataSource.setUsername(userNameForH2);
        dataSource.setPassword(passwordForH2);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplateForTest() {
        return new JdbcTemplate(dataSourceForTest());
    }

}
