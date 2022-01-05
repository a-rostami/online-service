package com.rostami.onlineservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@TestConfiguration
@ComponentScan("com.rostami.onlineservice")
@EnableAspectJAutoProxy
public class AppConfig {}

