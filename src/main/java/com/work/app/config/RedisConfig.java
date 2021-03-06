package com.work.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.work.app.model.Employee;

@Configuration
public class RedisConfig {
	
	@Bean
    public JedisConnectionFactory jedisConnectionFactory(){
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName("127.0.0.1");
		redisStandaloneConfiguration.setPort(6379);
		//redisStandaloneConfiguration.setPassword("1+vtkMmkpis/AZM+LbqkdoQJ8Ok9oVpwsiVWk2RywN63p9WVxFk+nRtColzg+sj2tLNsj2tLNom7Kf3BkgV5bC");
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
 		return jedisConnectionFactory;	
    }
	
	@Bean
	RedisTemplate<String, Employee> redisTemplate(){
		RedisTemplate<String, Employee> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
		
	}


}


