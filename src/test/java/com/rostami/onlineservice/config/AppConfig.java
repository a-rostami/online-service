package com.rostami.onlineservice.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@TestConfiguration
@ComponentScan("com.rostami.onlineservice")
@EnableAspectJAutoProxy
@EnableJpaAuditing
public class AppConfig {}