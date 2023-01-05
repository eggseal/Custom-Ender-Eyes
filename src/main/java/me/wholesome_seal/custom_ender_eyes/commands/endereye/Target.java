package me.wholesome_seal.custom_ender_eyes.commands.endereye;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import me.wholesome_seal.custom_ender_eyes.Main;
import me.wholesome_seal.custom_ender_eyes.commands.EnderEyeManager;
import me.wholesome_seal.custom_ender_eyes.structure.SubCommand;
import me.wholesome_seal.custom_ender_eyes.util.PrivateMessage;

public class Target extends SubCommand {
    private Main plugin;
    private FileConfiguration config;

    public Target(Main plugin, EnderEyeManager manager) {
        this.plugin = plugin;
        this.config = this.plugin.getConfig();

        this.name = "target";
        this.description = "Set to true if the eyes of ender should target the stronholds";
        this.args = new ArrayList<>();
        this.args.add(Arrays.asList("stronghold", "waypoint"));

        manager.subCommands.put(this.name, this);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length <= 1) {
            PrivateMessage.sendError(sender, "Missing args. Usage: /endereye target_stronghold <true/false>");
            return false;
        }
        boolean targetStronghold = args[1].equalsIgnoreCase("stronghold");
        this.config.set("target-strongholds", targetStronghold);
        this.plugin.saveConfig();

        String successMessage = targetStronghold 
            ? "Eyes of ender will now fly towards their original target" 
            : "Eyes of ender will now fly towards their nearest waypoint";
        PrivateMessage.sendSuccess(sender, successMessage);
        return true;
    }

    @Override
    public void updateArgs(CommandSender sender) {
        return;        
    }
    
}
