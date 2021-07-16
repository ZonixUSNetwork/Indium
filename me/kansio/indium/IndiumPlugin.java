package me.kansio.indium;

import cc.fyre.proton.Proton;
import cc.fyre.proton.command.param.defaults.PlayerParameterType;
import cc.fyre.venom.profile.provider.UUIDParameterProvider;
import me.kansio.indium.commands.TeleportCommand;
import me.kansio.indium.commands.TeleportHereCommand;
import me.kansio.indium.commands.TeleportPosCommand;
import me.kansio.indium.listeners.FilterListener;
import me.kansio.indium.listeners.MobDisableListener;
import me.kansio.indium.listeners.StaffChatListener;
import me.kansio.indium.manager.FilterManager;
import me.kansio.indium.manager.StaffChatManager;
import me.kansio.indium.redis.Payload;
import me.kansio.indium.redis.publisher.Publisher;
import me.kansio.indium.utils.Cooldowns;
import me.kansio.indium.utils.JsonBuilder;
import me.kansio.indium.utils.commandframework.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class IndiumPlugin extends JavaPlugin {

    private static IndiumPlugin instance;
    private Publisher publisher;
    private CommandFramework framework;
    private StaffChatManager staffChatManager = new StaffChatManager();
    private FilterManager filterManager = new FilterManager();

    @Override
    public void onEnable() {
        instance = this;
        framework = new CommandFramework(this);
        loadRedis();
        registerListener();
        registerCommands();
        registerCooldowns();
        this.filterManager.setFiltered();

        publisher.write(Payload.MESSAGE, new JsonBuilder()
                .add("message", "§7[§cServer Notifier§7] §c" + getServerName() + " §fis now §aonline§f.")
                .build());
    }


    @Override
    public void onDisable() {
        instance = null;
        publisher.write(Payload.MESSAGE, new JsonBuilder()
                .add("message", "§7[§cServer Notifier§7] §c" + getServerName() + " §fis now §coffline§f.")
                .build());
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer("§cServer is restarting...");
        }
    }

    public void registerCommands() {
        new TeleportCommand();
        new TeleportHereCommand();
        new TeleportPosCommand();
    }

    public void registerListener() {
        Proton.getInstance().getCommandHandler().registerParameterType(Player.class, new PlayerParameterType());
        Proton.getInstance().getCommandHandler().registerAll(this);

        Bukkit.getPluginManager().registerEvents(new StaffChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new FilterListener(), this);
        if (getServerName().contains("Hub")) {
            Bukkit.getPluginManager().registerEvents(new MobDisableListener(), this);
        }
    }

    public void registerCooldowns() {
        Cooldowns.createCooldown("request_cooldown");
    }

    public FilterManager getFilterManager() {
        return filterManager;
    }

    public CommandFramework getFramework() {
        return framework;
    }

    public static IndiumPlugin getInstance() {
        return instance;
    }

    public StaffChatManager getStaffChatManager() {
        return staffChatManager;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    private void loadRedis() {
        publisher = new Publisher("51.222.53.184", 6379, "PFxw2h2FVKS5PMWZ", false);
    }

    public String getServerName() {
        return Bukkit.getServerName();
    }
}
