package com.codigo.sunat.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.codigo.sunat.client")
public class SunatConfig {

}
