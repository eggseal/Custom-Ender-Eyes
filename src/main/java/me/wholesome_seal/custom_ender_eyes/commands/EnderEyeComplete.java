package me.wholesome_seal.custom_ender_eyes.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.wholesome_seal.custom_ender_eyes.Main;
import me.wholesome_seal.custom_ender_eyes.structure.SubCommand;

public class EnderEyeComplete implements TabCompleter {
    private Main plugin;
    private EnderEyeManager manager;

    public EnderEyeComplete(Main plugin, String name, EnderEyeManager manager) {
        this.plugin = plugin;
        this.manager = manager;

        this.plugin.getCommand(name).setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        HashMap<String, SubCommand> subCommands = manager.subCommands;
        int argIndex = args.length - 2;
        argIndex = argIndex >= 0 ? argIndex : 0;

        switch (args.length) {
            case 0: {
                break;
            }
            case 1: {
                return subCommands.keySet().parallelStream().toList();
            }
            default: {
                SubCommand subCommand = subCommands.get(args[0]);
                if (subCommand == null) break;
                if (subCommand.args == null) break;

                subCommand.updateArgs(sender);
                List<List<String>> subCommandArgs = subCommand.args;
                if (argIndex >= subCommandArgs.size()) break;

                return subCommandArgs.get(argIndex);
            }
        }
        return new ArrayList<String>();
    }
}
