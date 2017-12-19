package com.gi.ctdn.cloud.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600*24*7)
public class RedisSessionConfig {

    @Bean
    public JedisConnectionFactory connectionFactory()
    {
        JedisConnectionFactory connection = new JedisConnectionFactory();
        connection.setPort(6379);
        connection.setHostName("127.0.0.1");
        connection.setDatabase(0);
        return connection;
    }
}