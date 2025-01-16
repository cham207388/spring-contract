package com.abc.contracts.consumer;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import jakarta.persistence.EntityManagerFactory;

@SpringBootTest
// @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@ActiveProfiles("test")
class ConsumerApplicationTests {

    @MockitoBean
    private DataSource dataSource;
    @MockitoBean
    private EntityManagerFactory entityManagerFactory;

    @Test
    void contextLoads() {
    }

}
