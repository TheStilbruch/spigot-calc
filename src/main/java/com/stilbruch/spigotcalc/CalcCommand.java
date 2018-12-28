package com.stilbruch.spigotcalc;

import com.stilbruch.spigotcalc.gui.CalcGui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CalcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Error: Only players can run this command!");
            return true;
        }

        Player player = (Player) sender;
        player.sendMessage("Opening the gui...");
        new CalcGui().display(player);
        return true;
    }

}