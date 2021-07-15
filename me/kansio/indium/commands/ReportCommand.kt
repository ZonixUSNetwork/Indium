package me.kansio.indium.commands

import cc.fyre.proton.command.Command
import cc.fyre.proton.command.param.Parameter
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.Cooldowns
import me.kansio.indium.utils.JsonBuilder
import org.bukkit.entity.Player

object ReportCommand {

    @JvmStatic
    @Command(
        names = ["report"],
        permission = "",
    )
    fun execute(sender: Player, @Parameter(name = "message", wildcard = true) message: String) {
        if (Cooldowns.isOnCooldown("request_cooldown", sender)) {
            sender.sendMessage("§cYou cannot do this yet, you're on cooldown.")
            return;
        }
        Cooldowns.addCooldown("request_cooldown", sender, 60)
        sender.sendMessage("§aYour request has been submitted. All online staff members have been notified.")
        IndiumPlugin.getInstance().publisher.write(
            Payload.REQUEST, JsonBuilder()
            .add("server", IndiumPlugin.getInstance().serverName)
            .add("message", message)
            .add("sender", sender.name)
            .add("uuid", sender.uniqueId)
            .build())

    }

}