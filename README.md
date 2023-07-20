## Lettuce Connectivity Test

### Add connection properties to `application.properties` like:

```
redis.host=redis-14299.c1.asia-northeast1-1.gce.cloud.redislabs.com
redis.port=14299
redis.password=XXXXXXXXXXXXXXXX
redis.username=default
```

### Testing Against Local Redis Stack

```
# Server
redis_version:7.1.241
redis_git_sha1:00000000
redis_git_dirty:0
redis_build_id:d08e488269744889
redis_mode:standalone
os:Linux 5.15.49-linuxkit-pr x86_64
arch_bits:64
```

#### Redis CLI MONITOR output:

```
➜ redis-cli MONITOR
OK
1689877747.556881 [0 172.20.0.1:47670] "HELLO" "3" "AUTH" "(redacted)" "(redacted)"
1689877747.582568 [0 172.20.0.1:47670] "SET" "my_key" "Hi, there Redis!"
```

### Against Redis Cloud

Instance created Redis Stack 7.2.0.

#### Database info:

```
> INFO
# Server
redis_version:7.2.0
redis_git_sha1:00000000
redis_git_dirty:0
redis_build_id:0000000000000000000000000000000000000000
redis_mode:standalone
os:Linux 5.4.0-1044-gcp x86_64
arch_bits:64
```

#### MONITOR output:

```
11:41:06.865 [0 24.251.194.236:64659] "info"11:41:18.186 [0 24.251.194.236:64729] "HELLO" "3" "AUTH" "(redacted)" "(redacted)"
```

#### Stack trace with Lettuce `6.2.5.RELEASE`

```
/Users/briansam-bodden/.sdkman/candidates/java/17.0.3-zulu/zulu-17.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=64785:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/bsb/Code/lettuce-example/target/classes:/Users/bsb/.m2/repository/io/lettuce/lettuce-core/6.2.5.RELEASE/lettuce-core-6.2.5.RELEASE.jar:/Users/bsb/.m2/repository/io/netty/netty-common/4.1.94.Final/netty-common-4.1.94.Final.jar:/Users/bsb/.m2/repository/io/netty/netty-handler/4.1.94.Final/netty-handler-4.1.94.Final.jar:/Users/bsb/.m2/repository/io/netty/netty-resolver/4.1.94.Final/netty-resolver-4.1.94.Final.jar:/Users/bsb/.m2/repository/io/netty/netty-buffer/4.1.94.Final/netty-buffer-4.1.94.Final.jar:/Users/bsb/.m2/repository/io/netty/netty-transport-native-unix-common/4.1.94.Final/netty-transport-native-unix-common-4.1.94.Final.jar:/Users/bsb/.m2/repository/io/netty/netty-codec/4.1.94.Final/netty-codec-4.1.94.Final.jar:/Users/bsb/.m2/repository/io/netty/netty-transport/4.1.94.Final/netty-transport-4.1.94.Final.jar:/Users/bsb/.m2/repository/io/projectreactor/reactor-core/3.4.31/reactor-core-3.4.31.jar:/Users/bsb/.m2/repository/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar com.redis.lettuce.Main
Exception in thread "main" io.lettuce.core.RedisConnectionException: Unable to connect to redis-14299.c1.asia-northeast1-1.gce.cloud.redislabs.com/<unresolved>:14299
	at io.lettuce.core.RedisConnectionException.create(RedisConnectionException.java:78)
	at io.lettuce.core.RedisConnectionException.create(RedisConnectionException.java:56)
	at io.lettuce.core.AbstractRedisClient.getConnection(AbstractRedisClient.java:350)
	at io.lettuce.core.RedisClient.connect(RedisClient.java:216)
	at io.lettuce.core.RedisClient.connect(RedisClient.java:201)
	at com.redis.lettuce.Main.main(Main.java:32)
Caused by: io.lettuce.core.RedisCommandTimeoutException: Connection initialization timed out after 1 minute(s)
	at io.lettuce.core.protocol.RedisHandshakeHandler.lambda$channelRegistered$0(RedisHandshakeHandler.java:67)
	at io.netty.util.concurrent.PromiseTask.runTask(PromiseTask.java:98)
	at io.netty.util.concurrent.PromiseTask.run(PromiseTask.java:106)
	at io.netty.util.concurrent.AbstractEventExecutor.runTask(AbstractEventExecutor.java:174)
	at io.netty.util.concurrent.DefaultEventExecutor.run(DefaultEventExecutor.java:66)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:833)

Process finished with exit code 1
```