package de.kelanisystem.kelaniconfig;

import org.simpleyaml.configuration.Configuration;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class KelaniYamlFile extends YamlFile {
    private String name;

    public KelaniYamlFile(File file, String name) throws IOException, InvalidConfigurationException {
        super(file);

        setName(name);

        if (!exists())
            createNewFile(true);

        load();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addDefault(String path, Object value) {
        super.addDefault(path, value);
        options().copyDefaults(true);
    }

    @Override
    public void addDefaults(Configuration configuration) {
        super.addDefaults(configuration);
        options().copyDefaults(true);
    }

    @Override
    public void addDefaults(Map<String, Object> configuration) {
        super.addDefaults(configuration);
        options().copyDefaults(true);
    }
}
