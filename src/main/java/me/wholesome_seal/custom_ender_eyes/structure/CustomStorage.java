package me.wholesome_seal.custom_ender_eyes.structure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.wholesome_seal.custom_ender_eyes.Main;

public class CustomStorage {
    public static File file;
    public static FileConfiguration config;

    public static void setup(Main plugin) {
        CustomStorage.file = new File(plugin.getDataFolder(), "waypoints.yml");

        if (!CustomStorage.file.exists()) {
            try {
                CustomStorage.file.createNewFile();
            } catch (IOException exception) {
                System.out.println("[CustomEnderEyes] Error: Failed to generate session file.");
                System.out.println(exception);
                return;
            }
        }
        CustomStorage.config = YamlConfiguration.loadConfiguration(CustomStorage.file);

        CustomStorage.setDefaults();
        CustomStorage.config.options().copyDefaults();
        CustomStorage.save();
    }

    private static void setDefaults() {
        config.addDefault("waypoint.overworld", new ArrayList<WayPointBuilder>());
        config.addDefault("waypoint.nehter", new ArrayList<WayPointBuilder>());
        config.addDefault("waypoint.end", new ArrayList<WayPointBuilder>());
    }

    public static void save() {
        try {
            CustomStorage.config.save(CustomStorage.file);
        } catch (IOException exception) {
            System.out.println("[CustomEnderEyes] Error: Failed to save data to session file.");
        } 
        reload();
    }

    public static void reload() {
        CustomStorage.config = YamlConfiguration.loadConfiguration(CustomStorage.file);
    }
}