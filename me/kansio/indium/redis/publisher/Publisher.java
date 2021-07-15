package me.kansio.indium.redis.publisher;

import com.google.gson.JsonObject;
import me.kansio.indium.redis.Payload;
import me.kansio.indium.utils.Pair;
import org.bukkit.Bukkit;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Publisher {

    private RedissonClient redissonClient;

    private RTopic<Pair<Payload, String>> pubSub;

    private ExecutorService localRedisThread;

    public Publisher(String host, int port, String password, boolean auth) {
        Config config = new Config();

        config.useSingleServer().setAddress(host + ':' + port);
        config.useSingleServer().setPassword(password);

        redissonClient = Redisson.create(config);

        pubSub = redissonClient.getTopic("INDIUM:PUBSUB");

        localRedisThread = Executors.newFixedThreadPool(5);

        new Thread(() -> pubSub.addListener(new PublisherListener())).start();
    }

    public void write(Payload payload, JsonObject data) {
        if (Bukkit.isPrimaryThread()) {
            localRedisThread.execute(() -> pubSub.publish(new Pair<>(payload, data.toString())));
            return;
        }

        pubSub.publish(new Pair<>(payload, data.toString()));
    }
}
