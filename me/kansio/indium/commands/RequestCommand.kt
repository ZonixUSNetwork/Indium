package me.kansio.indium.commands

import cc.fyre.proton.command.Command
import cc.fyre.proton.command.param.Parameter
import cc.fyre.venom.VenomAPI
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.Cooldowns
import me.kansio.indium.utils.JsonBuilder
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object RequestCommand {

    @JvmStatic
    @Command(
        names = ["request"],
        permission = "",
    )
    fun execute(sender: Player, @Parameter(name = "message", wildcard = true) message: String) {
        if (Cooldowns.isOnCooldown("request_cooldown", sender)) {
            sender.sendMessage("§cYou cannot do this yet, you're on cooldown.")
            return;
        }
        Cooldowns.addCooldown("request_cooldown", sender, 60)
        sender.sendMessage("§aYour report has been submitted. All online staff members have been notified.")
        IndiumPlugin.getInstance().publisher.write(Payload.REQUEST, JsonBuilder()
            .add("server", IndiumPlugin.getInstance().serverName)
            .add("message", message)
            .add("sender", VenomAPI.instance.grantHandler.findBestRank(sender.uniqueId).color.replace("&", "§") + sender.name)
            .add("uuid", sender.uniqueId)
            .build())
    }

}