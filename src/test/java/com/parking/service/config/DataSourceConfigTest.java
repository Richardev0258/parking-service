package com.parking.service.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class DataSourceConfigTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void dataSourceShouldBeLoaded() {
        assertNotNull(dataSource, "DataSource must not be null");
    }
}
