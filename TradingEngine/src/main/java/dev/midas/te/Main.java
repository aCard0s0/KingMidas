package dev.midas.te;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Main {

    public static void main(String[] args) {

        Jedis jSubscriber = new Jedis("localhost");

        jSubscriber.subscribe(new JedisPubSub() {

            @Override
            public void onMessage(String channel, String message) {
                System.out.println(message);
            }
        }, "channel");
    }
}
