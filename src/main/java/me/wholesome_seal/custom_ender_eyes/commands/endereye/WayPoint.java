package me.wholesome_seal.custom_ender_eyes.commands.endereye;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.wholesome_seal.custom_ender_eyes.Main;
import me.wholesome_seal.custom_ender_eyes.commands.EnderEyeManager;
import me.wholesome_seal.custom_ender_eyes.structure.CustomStorage;
import me.wholesome_seal.custom_ender_eyes.structure.SubCommand;
import me.wholesome_seal.custom_ender_eyes.structure.WayPointBuilder;
import me.wholesome_seal.custom_ender_eyes.util.PrivateMessage;

public class WayPoint extends SubCommand {
    private Main plugin;
    private FileConfiguration config;

    public WayPoint(Main plugin, EnderEyeManager manager) {
        this.plugin = plugin;
        this.config = this.plugin.getConfig();

        this.name = "waypoint";
        this.description = "Add/Remove a waypoint by name";
        this.args = new ArrayList<>();
        this.args.add(Arrays.asList("create", "remove"));

        manager.subCommands.put(this.name, this);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            PrivateMessage.sendError(sender, "This command should be executed by players only");
            return false;
        }
        if (args.length < 3) {
            PrivateMessage.sendError(sender, "Missing arguments. Usage: /endereye waypoint <create/remove> <name>");
            return false;
        }

        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        String waypointStoragePath;

        switch(player.getWorld().getEnvironment()) {
            case NORMAL: {
                waypointStoragePath = "waypoint.overworld";
                break;
            }
            case NETHER: {
                waypointStoragePath = "waypoint.nether";
                if (this.config.getBoolean("allow-nether")) break; 
                PrivateMessage.sendPrivate(player, "Waypoints are not enabled on the nether");
                return false;
            }
            case THE_END: {
                waypointStoragePath = "waypoint.end";
                if (this.config.getBoolean("allow-end")) break;
                PrivateMessage.sendPrivate(player, "Waypoints are not enabled on the end");
                return false;
            }
            default: {
                PrivateMessage.sendPrivate(player, "Waypoints are not enabled on custom environments");
                return false;
            }
        }

        String name = args[2];
        ArrayList<WayPointBuilder> currentWaypoints = WayPointBuilder.getWayPoint(waypointStoragePath);

        switch(args[1]) {
            case "create": {
                WayPointBuilder waypoint = new WayPointBuilder(name, playerLocation);
                currentWaypoints.add(waypoint);

                PrivateMessage.sendSuccess(player, "Added this location as a waypoint");
                break;
            }
            case "remove": {
                currentWaypoints.removeIf((WayPointBuilder waypoint) -> waypoint.name == null || waypoint.name.equalsIgnoreCase(name));

                PrivateMessage.sendSuccess(player, "Removed all waypoints named \"" + name + "\"");
                break;
            }
            default: {
                PrivateMessage.sendError(player, "Invalid arguments. Usage: /endereye waypoint <create/remove> <name>");
                return false;
            }
        }

        CustomStorage.config.set(waypointStoragePath, currentWaypoints);
        CustomStorage.save();
        return true;
    }

    @Override
    public void updateArgs(CommandSender sender) {
        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;
        Environment playerEnvironment = player.getWorld().getEnvironment();

        String waypointStoragePath;
        switch(playerEnvironment) {
            case NORMAL: {
                waypointStoragePath = "waypoint.overworld";
                break;
            }
            case NETHER: {
                waypointStoragePath = "waypoint.nether";
                if (this.config.getBoolean("allow-nether")) break; 
                return;
            }
            case THE_END: {
                waypointStoragePath = "waypoint.end";
                if (this.config.getBoolean("allow-end")) break;
                return;
            }
            default: {
                return;
            }
        }

        List<WayPointBuilder> currentWaypoints = WayPointBuilder.getWayPoint(waypointStoragePath);
        List<String> waypointNames = new ArrayList<>(currentWaypoints).stream().map((WayPointBuilder waypoint) -> {
            return waypoint.name == null ? "null" : waypoint.name;
        }).toList();

        if (this.args.size() <= 1) this.args.add(waypointNames);
        else this.args.set(1, waypointNames);
    }
}
