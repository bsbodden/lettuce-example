package com.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.io.IOException;
import java.util.Properties;

public class Main {
  public static void main(String[] args) {
    Properties props = new Properties();
    try {
      props.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));

    } catch (IOException e) {
      e.printStackTrace();
    }

    String host = props.getProperty("redis.host", "localhost");
    String port = props.getProperty("redis.port", "6379");
    String password = props.getProperty("redis.password", "");
    String username = props.getProperty("redis.username", "");

    var redisUri = RedisURI.Builder
        .redis(host, Integer.parseInt(port))
        .withAuthentication(username, password.toCharArray())
        .build();

    try (RedisClient redisClient = RedisClient.create(redisUri)) {
      StatefulRedisConnection<String, String> connection = redisClient.connect();
      RedisCommands<String, String> syncCommands = connection.sync();

      syncCommands.set("my_key", "Hi, there Redis!");

      String result = syncCommands.get("my_key");
      System.out.println("👍🏾 It worked, the value of 'my_key' is: " + result);

      connection.close();
      redisClient.shutdown();
    }
  }
}