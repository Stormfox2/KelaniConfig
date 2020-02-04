package de.kelanisystem.kelaniconfig;

import org.simpleyaml.configuration.file.YamlConfiguration;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KelaniConfigManager {
    //PushTest
    private Map<String, YamlFile> configs;
    private File configFolder = new File("data/");

    public KelaniConfigManager() {
        this.configs = new HashMap<>();
    }

    public KelaniConfigManager(File configFolder) {
        this.configFolder = configFolder;
    }

    public List<YamlConfiguration> createFile(String... name) throws IOException, InvalidConfigurationException {
        return createFile(configFolder, name);
    }

    public List<YamlConfiguration> createFile(File path, String... names) throws IOException, InvalidConfigurationException {
        List<YamlConfiguration> configurations = new ArrayList<>();

        if (!path.exists())
            if (!configFolder.mkdir()) throw new IOException("Could not create " + configFolder.getName());

        for (String name : names) {
            YamlFile yamlFile = new YamlFile(path + "/" + name + ".yml");

            if (!yamlFile.exists())
                yamlFile.createNewFile(true);

            yamlFile.load();


            configs.put(name, yamlFile);
            configurations.add(yamlFile);
        }

        return configurations;
    }

    public YamlConfiguration getConfig(String config) {
        return configs.get(config);
    }

    public void copyDefaults(String config, boolean bool) throws IOException {
        this.configs.get(config).options().copyDefaults(bool);

        save(config);
    }

    public void addDefault(String config, String key, Object value) {
        this.configs.get(config).addDefault(key, value);

    }

    public void addDefaults(String config, Map<String, Object> defaults) {
            this.configs.get(config).addDefaults(defaults);
        }

        public Object get(String config, String key) {
            return this.configs.get(config).get(key);
        }

        public String getString(String config, String key) {
            return this.configs.get(config).getString(key);
        }

        public Boolean getBoolean(String config, String key) {
            return this.configs.get(config).getBoolean(key);
        }

        public Integer getInteger(String config, String key) {
            return this.configs.get(config).getInt(key);
        }

    public List<String> getStringList(String config, String key) {
        return this.configs.get(config).getStringList(key);
    }

    public List<Integer> getIntegerList(String config, String key) {
        return this.configs.get(config).getIntegerList(key);
    }

    public List<?> getList(String config, String key) {
        return this.configs.get(config).getList(key);
    }


    public void setHeader(String config, String header) throws IOException {
        this.configs.get(config).options().header(header);
        save(config);
    }

    public void save(String config) throws IOException {
        this.configs.get(config).save();
    }
}
