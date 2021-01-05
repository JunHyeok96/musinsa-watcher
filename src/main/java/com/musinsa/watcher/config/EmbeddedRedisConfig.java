package com.musinsa.watcher.config;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

  @Value("${spring.redis.port}")
  private int redisPort;

  private static RedisServer redisServer;

  @PostConstruct
  public void redisServer() throws IOException {
    if (redisServer == null) {
      redisServer = new RedisServer(redisPort);
      redisServer.start();
    }
  }

  @PreDestroy
  public void stopRedis() {
    redisServer.stop();
  }

}
