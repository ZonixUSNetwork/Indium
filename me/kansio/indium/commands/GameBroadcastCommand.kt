package me.kansio.indium.commands

import cc.fyre.proton.command.Command
import cc.fyre.venom.VenomAPI
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.Cooldowns
import me.kansio.indium.utils.JsonBuilder
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object GameBroadcastCommand {

    @JvmStatic
    @Command(
        names = ["bc"],
        permission = "rank.donator",
    )
    fun execute(sender: Player) {
        if (Cooldowns.isOnCooldown("broadcast_cooldown", sender)) {
            sender.sendMessage("§cYou cannot do this yet, please wait.")
            return
        }
        if (!IndiumPlugin.getInstance().config.getBoolean("gameserver")) {
            sender.sendMessage("§cYou cannot use this command on this server.")
            return
        }
        Cooldowns.addCooldown("broadcast_cooldown", sender, 60)
        var name = VenomAPI.instance.grantHandler.findBestRank(sender.uniqueId).color.replace("&", "§") + sender.name
        var message = "§8[§4Games§8] §f<$name§f> §fJoin ${Bukkit.getServerName()}! /join ${IndiumPlugin.getInstance().config.getString("server")}"
        IndiumPlugin.getInstance().publisher.write(Payload.BROADCAST, JsonBuilder().add("message", message).build())
    }
}