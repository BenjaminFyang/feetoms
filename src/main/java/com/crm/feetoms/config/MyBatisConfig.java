package com.crm.feetoms.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan("com.crm.feetoms.mapper")
public class MyBatisConfig {
}
