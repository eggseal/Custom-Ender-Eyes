package me.wholesome_seal.custom_ender_eyes.structure;

import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public String name;
    public String description;
    public List<List<String>> args;

    public abstract boolean execute(CommandSender sender, String[] args);

    public abstract void updateArgs(CommandSender sender);
}
