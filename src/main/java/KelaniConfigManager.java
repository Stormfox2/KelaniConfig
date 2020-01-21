package main.java;

import org.simpleyaml.configuration.file.YamlConfiguration;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KelaniConfigManager {
        private Map<String, YamlFile> configs;
        private File configFolder = new File("data/");

        public KelaniConfigManager(String... configs) {
            this.configs = new HashMap<>();

            createFile(configs);
        }

        public KelaniConfigManager(File configFolder, String... configs){
            this(configs);
            this.configFolder = configFolder;
        }

        public void createFile(String... name) {
            createFile(configFolder, name);
        }

        public void createFile(File path, String... names ) {
            if(!path.exists())
                configFolder.mkdir();

            for(String name : names) {
                YamlFile yamlFile = new YamlFile(path + "/" + name +".yml");

                try {
                    if(!yamlFile.exists()){
                        System.out.println("Creating new Configuration: " + name + ".yml");
                        yamlFile.createNewFile(true);
                    }
                    else
                        System.out.println("Loading new Configuration: " + name + ".yml");
                    yamlFile.load();

                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                configs.put(name, yamlFile);
            }
        }

    public YamlConfiguration getConfig(String config) {
        return configs.get(config);
    }

        public void copyDefaults(String config, boolean bool) {
            this.configs.get(config).options().copyDefaults(bool);

            save(config);
        }

        public void addDefault(String config, String key, Object value) {
            this.configs.get(config).addDefault(key, value);
            System.out.println("added Default to " + config + " | " + key + " | " + value);

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


        public void setHeader(String config, String header) {
            this.configs.get(config).options().header(header);
            save(config);
        }
        public void save(String config) {
            try {
                this.configs.get(config).save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
