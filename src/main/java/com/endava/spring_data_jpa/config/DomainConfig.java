package com.endava.spring_data_jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.endava.spring_data_jpa.domain")
@EnableJpaRepositories("com.endava.spring_data_jpa.repos")
@EnableTransactionManagement
public class DomainConfig {
}
