package me.wholesome_seal.custom_ender_eyes;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import me.wholesome_seal.custom_ender_eyes.commands.EnderEyeManager;
import me.wholesome_seal.custom_ender_eyes.events.EnderEyeRedirect;
import me.wholesome_seal.custom_ender_eyes.structure.CustomStorage;
import me.wholesome_seal.custom_ender_eyes.structure.WayPointBuilder;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
        
        //  SETUP
        ConfigurationSerialization.registerClass(WayPointBuilder.class, "WayPoint");
        CustomStorage.setup(this);

        //  COMMANDS
        new EnderEyeManager(this);

        //  EVENTS
        new EnderEyeRedirect(this);
    }
}
