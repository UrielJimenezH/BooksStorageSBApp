package com.example.booksStorage;

import org.springframework.context.annotation.Bean;
<<<<<<< HEAD
=======
import org.springframework.context.annotation.ComponentScan;
>>>>>>> 49abad2 (Integration of Book service with DB through JDBCTemplate)
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
<<<<<<< HEAD
=======
@ComponentScan(basePackages = { "book" })//Todo add letter, magazine and newspaper
>>>>>>> 49abad2 (Integration of Book service with DB through JDBCTemplate)
public class DataSourceConfiguration {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/BooksStorage");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource());
    }
}
