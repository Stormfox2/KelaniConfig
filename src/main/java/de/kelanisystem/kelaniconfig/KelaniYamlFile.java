package de.kelanisystem.kelaniconfig;

import org.simpleyaml.configuration.Configuration;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class KelaniYamlFile extends YamlFile {
    private String name;

    /**
     * Creates / Loads a config file
     *
     * @param file file of the config
     * @param name name of the config
     * @throws IOException                   thrown if file could not been found
     * @throws InvalidConfigurationException thrown if file is invalid
     */
    public KelaniYamlFile(File file, String name) throws IOException, InvalidConfigurationException {
        super(file);

        setName(name);

        if (!exists())
            createNewFile(true);

        load();
    }

    /**
     * Get name of config
     *
     * @return name of config
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the config
     *
     * @param name desired name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a default to the config file and activates copy-defaults option
     *
     * @param key   Key of the default entry
     * @param value Value of the default entry
     */
    @Override
    public void addDefault(String key, Object value) {
        super.addDefault(key, value);

        //Activate copy defaults
        options().copyDefaults(true);
    }

    /**
     * Adds multiple default entries and activates copy-defaults option
     *
     * @param configuration Configuration with the default entries
     */
    @Override
    public void addDefaults(Configuration configuration) {
        super.addDefaults(configuration);
        options().copyDefaults(true);
    }

    /**
     * Adds multiple default entries and activates copy-defaults option
     *
     * @param configuration Map with the default entries
     */
    @Override
    public void addDefaults(Map<String, Object> configuration) {
        super.addDefaults(configuration);
        options().copyDefaults(true);
    }
}
