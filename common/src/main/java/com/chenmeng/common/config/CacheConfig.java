package com.chenmeng.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * 缓存配置
 *
 * @author chenmeng
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * 配置RedisCacheManager bean，用于管理缓存。
     *
     * 该方法通过RedisCacheConfiguration的默认配置，禁用缓存空值，并使用GenericJackson2JsonRedisSerializer对值进行序列化。
     * 然后，利用redisConnectionFactory()返回的RedisConnectionFactory构建RedisCacheManager。
     *
     * @return RedisCacheManager，用于缓存管理。
     */
    @Bean
    public CacheManager cacheManager() {
        // 配置Redis缓存的默认设置，包括禁用缓存空值和设置值的序列化方式。
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 使用配置的RedisCacheConfiguration构建RedisCacheManager，并返回。
        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

}
