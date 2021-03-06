package de.kelanisystem.kelaniconfig;

import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KelaniConfigManager {
    private final String separator = System.getProperty("file.separator");

    private final Map<String, KelaniYamlFile> configs;
    private Path configFolder = Paths.get("data/");

    /**
     * Creates new instance of the KelaniConfigManager
     * Default configfolder is data/
     */
    public KelaniConfigManager() {
        this.configs = new HashMap<>();
    }

    /**
     * Creates new instance of the KelaniConfigManager
     *
     * @param configFolder default folder from configs
     */
    public KelaniConfigManager(Path configFolder) {
        this.configFolder = configFolder;
        configs = new HashMap<>();
    }

    /**
     * Creates config(s) default path
     *
     * @param names of config(s) you want to create
     * @return Configs you created
     * @throws IOException                   On save
     * @throws InvalidConfigurationException On load
     */
    public List<KelaniYamlFile> createFile(String... names) throws IOException, InvalidConfigurationException {
        return createFileWithOwnPath(configFolder, names);
    }

    /**
     * Creates config(s) with different path
     *
     * @param name of config(s) you want to create
     * @return Configs you created
     * @throws IOException                   On save
     * @throws InvalidConfigurationException On load
     */
    public List<KelaniYamlFile> createFile(String path, String name) throws IOException, InvalidConfigurationException {
        List<KelaniYamlFile> configurations = new ArrayList<>();

        Path newPath = Paths.get(configFolder.toString(), path);

        return createFileWithOwnPath(newPath, name);
    }

    /**
     * Creates config(s) with different path
     *
     * @param names of config(s) you want to create
     * @return Configs you created
     * @throws IOException                   On save
     * @throws InvalidConfigurationException On load
     */
    public List<KelaniYamlFile> createFileWithOwnPath(Path path, String... names) throws IOException, InvalidConfigurationException {
        List<KelaniYamlFile> configurations = new ArrayList<>();

        if (!Files.exists(path)) Files.createDirectory(path);

        for (String name : names) {
            KelaniYamlFile yamlFile = new KelaniYamlFile(new File(path + separator + name + ".yml"), name);

            configs.put(name, yamlFile);
            configurations.add(yamlFile);
        }

        return configurations;
    }


    /**
     * Returns configfile by name
     *
     * @param configName name of the config
     * @return YamlConfiguration file
     */
    public KelaniYamlFile getConfig(String configName) {
        return configs.get(configName);
    }

    /**
     * Sets copy defaults option and saves
     *
     * @param configName name of the config you want to edit
     * @param bool       param of the option
     * @throws IOException on save
     */
    public void copyDefaults(String configName, boolean bool) throws IOException {
        this.configs.get(configName).options().copyDefaults(bool);

        save(configName);
    }

    /**
     * Copies a default into config.
     * Remember setting copy-defaults
     *
     * @param configName name of the config you want to edit
     * @param key        key of default
     * @param value      value of default
     */
    public void addDefault(String configName, String key, Object value) {
        this.configs.get(configName).addDefault(key, value);

    }

    /**
     * Copies multiple defaults into config.
     * Remember setting copy defaults
     *
     * @param configName name of the config you want to edit
     * @param defaults   defaults you want to set
     */
    public void addDefaults(String configName, Map<String, Object> defaults) {
        this.configs.get(configName).addDefaults(defaults);
    }

    /**
     * Gets an object out of config
     *
     * @param configName name of config you want to read
     * @param key        key of the value you want to get
     * @return value
     */
    public Object get(String configName, String key) {
        return this.configs.get(configName).get(key);
    }

    /**
     * Gets an string out of config
     *
     * @param configName name of config you want to read
     * @param key        key of the value you want to get
     * @return value
     */
    public String getString(String configName, String key) {
        return this.configs.get(configName).getString(key);
    }

    /**
     * Gets an boolean out of config
     *
     * @param configName name of config you want to read
     * @param key        key of the value you want to get
     * @return value
     */
    public Boolean getBoolean(String configName, String key) {
        return this.configs.get(configName).getBoolean(key);
    }

    /**
     * Gets an integer out of config file
     *
     * @param configName name of config you want to read
     * @param key        key of the value you want to get
     * @return value
     */
    public Integer getInteger(String configName, String key) {
        return this.configs.get(configName).getInt(key);
    }

    /**
     * Gets an string list out of config
     *
     * @param configName name of config you want to read
     * @param key        key of the value you want to get
     * @return value
     */
    public List<String> getStringList(String configName, String key) {
        return this.configs.get(configName).getStringList(key);
    }

    /**
     * Gets an integer list out of config
     *
     * @param configName name of config you want to read
     * @param key        key of the value you want to get
     * @return value
     */
    public List<Integer> getIntegerList(String configName, String key) {
        return this.configs.get(configName).getIntegerList(key);
    }

    /**
     * Gets an list out of config
     *
     * @param configName name of config you want to read
     * @param key        key of the value you want to get
     * @return value
     */
    public List<?> getList(String configName, String key) {
        return this.configs.get(configName).getList(key);
    }

    /**
     * Set header rows of the config.
     * For comments in the file
     *
     * @param configName name of the config
     * @param header     displayed text
     * @throws IOException throws on save
     */
    public void setHeader(String configName, String header) throws IOException {
        this.configs.get(configName).options().header(header);
        save(configName);
    }

    /**
     * Saves specific config
     *
     * @param configName name of the config
     * @throws IOException throws on save
     */
    public void save(String configName) throws IOException {
        this.configs.get(configName).save();
    }
}
