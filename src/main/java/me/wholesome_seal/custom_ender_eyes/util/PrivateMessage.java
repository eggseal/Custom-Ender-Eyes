package me.wholesome_seal.custom_ender_eyes.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PrivateMessage {
    private static boolean argsNull(CommandSender sender, String message) {
        return sender == null || message == null;
    }

    public static void sendError(CommandSender sender, String message) {
        if (argsNull(sender, message)) return;
        
        String errorMessage = ChatColor.GREEN + "[EnderEye] " + ChatColor.RED + message;
        sender.sendMessage(errorMessage);
    }

    public static void sendPrivate(CommandSender sender, String message) {
        if (argsNull(sender, message)) return;

        String privateMessage = ChatColor.GREEN + "[EnderEyes] " + ChatColor.GRAY + message;
        sender.sendMessage(privateMessage);
    }

    public static void sendSuccess(CommandSender sender, String message) {
        if (argsNull(sender, message)) return;

        String succMessage = ChatColor.GREEN + "[EnderEyes] " + ChatColor.AQUA + message;
        sender.sendMessage(succMessage);
    }
}
