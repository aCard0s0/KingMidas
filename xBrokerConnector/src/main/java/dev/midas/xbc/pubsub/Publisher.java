package dev.midas.xbc.pubsub;

import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;

public class Publisher {

    private final Jedis jPublisher;

    public Publisher() {
        jPublisher = new Jedis("localhost");
    }

    public void publish(String message) {
        byte[] channel = "channel".getBytes(StandardCharsets.UTF_8);
        byte[] encoded = message.getBytes(StandardCharsets.UTF_8);
        jPublisher.publish(channel, encoded);
    }
}
