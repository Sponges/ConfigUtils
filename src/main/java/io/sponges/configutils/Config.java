package io.sponges.configutils;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private final String name;

    private final File file;
    private final FileConfiguration configuration;

    public Config(String name, File file, FileConfiguration configuration) {
        this.name = name;
        this.file = file;
        this.configuration = configuration;
    }

    public void save() throws IOException {
        configuration.save(file);
    }

    public String getName() {
        return name;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

}