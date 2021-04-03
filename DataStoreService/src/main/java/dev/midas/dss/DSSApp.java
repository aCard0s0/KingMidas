package dev.midas.dss;

import dev.midas.dss.core.StoreEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DSSApp implements CommandLineRunner {

    @Autowired
    private StoreEngine engine;

    public static void main(String[] args) {
        SpringApplication.run(DSSApp.class, args);
    }

    @Override
    public void run(String... args) {
        engine.startDefaultConfigs();
    }
}
