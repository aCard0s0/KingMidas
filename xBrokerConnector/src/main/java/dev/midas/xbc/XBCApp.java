package dev.midas.xbc;

import dev.midas.xbc.config.ConfigManager;
import dev.midas.xbc.stream.ListeningEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XBCApp implements CommandLineRunner{

    @Autowired
    private final ListeningEngine listening;

    public XBCApp(ListeningEngine listening) {
        this.listening = listening;
    }

    public static void main(String[] args) {
        SpringApplication.run(XBCApp.class, args);
    }

    @Override
    public void run(String... args) {
        listening.startDefaultListeners();
    }
}
