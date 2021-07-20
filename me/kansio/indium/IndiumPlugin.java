package me.kansio.indium;

import cc.fyre.proton.Proton;
import cc.fyre.proton.command.param.defaults.PlayerParameterType;
import me.kansio.indium.commands.TeleportCommand;
import me.kansio.indium.commands.TeleportHereCommand;
import me.kansio.indium.commands.TeleportPosCommand;
import me.kansio.indium.guis.punishments.PunishmentGui;
import me.kansio.indium.guis.reportgui.ReportsGui;
import me.kansio.indium.guis.reportoptions.ReportOptions;
import me.kansio.indium.listeners.FilterListener;
import me.kansio.indium.listeners.FreezeListener;
import me.kansio.indium.listeners.MobDisableListener;
import me.kansio.indium.listeners.StaffChatListener;
import me.kansio.indium.manager.*;
import me.kansio.indium.redis.Payload;
import me.kansio.indium.redis.publisher.Publisher;
import me.kansio.indium.utils.Cooldowns;
import me.kansio.indium.utils.JsonBuilder;
import me.kansio.indium.utils.commandframework.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class IndiumPlugin extends JavaPlugin {

    private static IndiumPlugin instance;
    private Publisher publisher;
    private CommandFramework framework;
    private final StaffChatManager staffChatManager = new StaffChatManager();
    private final FilterManager filterManager = new FilterManager();
    private final DisguiseManager disguiseManager = new DisguiseManager();
    private final ReportManager reportManager = new ReportManager();
    private final ReportsGui reportsGui = new ReportsGui();
    private ReportOptions reportOptions = new ReportOptions();
    private FreezeManager freezeManager = new FreezeManager();
    private PunishmentGui punishmentGui = new PunishmentGui();

    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        instance = this;
        framework = new CommandFramework(this);
        loadRedis();
        registerListener();
        registerCommands();
        registerCooldowns();
        this.saveDefaultConfig();
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
        Bukkit.getPluginManager().registerEvents(new PunishmentGui(), this);
        Bukkit.getPluginManager().registerEvents(new FreezeListener(), this);
        Bukkit.getPluginManager().registerEvents(new FilterListener(), this);
        Bukkit.getPluginManager().registerEvents(new ReportOptions(), this);
        Bukkit.getPluginManager().registerEvents(new ReportsGui(), this);
        if (getServerName().contains("Hub")) {
            Bukkit.getPluginManager().registerEvents(new MobDisableListener(), this);
        }
    }

    public void registerCooldowns() {
        Cooldowns.createCooldown("request_cooldown");
        Cooldowns.createCooldown("report_cooldown");
        Cooldowns.createCooldown("broadcast_cooldown");
    }

    public FreezeManager getFreezeManager() {
        return freezeManager;
    }

    public PunishmentGui getPunishmentGui() {
        return punishmentGui;
    }

    public ReportsGui getReportsGui() {
        return reportsGui;
    }

    public ReportOptions getReportOptions() {
        return reportOptions;
    }

    public FilterManager getFilterManager() {
        return filterManager;
    }

    public DisguiseManager getDisguiseManager() {
        return disguiseManager;
    }

    public ReportManager getReportManager() {
        return reportManager;
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
