package de.kelanisystem.kelaniconfig;

import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;

public class KelaniYamlFile extends YamlFile {
    private String name;

    public KelaniYamlFile(File file) {
        super(file);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
