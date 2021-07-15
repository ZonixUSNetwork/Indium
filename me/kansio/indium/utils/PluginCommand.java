package me.kansio.indium.utils;

import me.kansio.indium.IndiumPlugin;
import me.kansio.indium.utils.commandframework.CommandArgs;
import org.bukkit.Bukkit;

public abstract class PluginCommand {

    public IndiumPlugin main = IndiumPlugin.getInstance();

    public PluginCommand() {
        if (main.getFramework() == null) Bukkit.broadcastMessage("null framework???");
        main.getFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}