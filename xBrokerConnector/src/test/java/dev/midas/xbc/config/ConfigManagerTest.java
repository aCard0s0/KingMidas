package dev.midas.xbc.config;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class ConfigManagerTest {

    private ConfigManager victim;

    @BeforeMethod
    public void setUp() {
        victim = new ConfigManager();
    }

    @Test(description = "")
    public void shouldSuccessOnLoad() {}

    @Test(description = "", expectedExceptions = FileNotFoundException.class)
    public void shouldFailOnLoad() {}
}
