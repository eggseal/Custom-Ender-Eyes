package me.wholesome_seal.custom_ender_eyes.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.wholesome_seal.custom_ender_eyes.Main;
import me.wholesome_seal.custom_ender_eyes.commands.endereye.Target;
import me.wholesome_seal.custom_ender_eyes.commands.endereye.WayPoint;
import me.wholesome_seal.custom_ender_eyes.structure.SubCommand;
import me.wholesome_seal.custom_ender_eyes.util.PrivateMessage;

public class EnderEyeManager implements CommandExecutor {
    private Main plugin;

    private String name = "endereye";
    public HashMap<String, SubCommand> subCommands = new HashMap<String, SubCommand>();

    public EnderEyeManager(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand(this.name).setExecutor(this);

        new EnderEyeComplete(this.plugin, this.name, this);

        //  SUBCOMMANDS
        new WayPoint(this.plugin, this);
        new Target(this.plugin, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            PrivateMessage.sendError(sender, "A subcommand must be provided");
            return false;
        }

        String inputSubCommand = args[0];
        SubCommand subCommand = this.subCommands.get(inputSubCommand);
        if (subCommand == null) {
            PrivateMessage.sendError(sender, "This subcommand does not exist");
            return false;
        }

        if (!sender.hasPermission("customendereyes.endereye")) {
            PrivateMessage.sendError(sender, "Missing permissions: customendereyes.endereye");
            return false;
        }

        return subCommand.execute(sender, args);
    }

    
}
