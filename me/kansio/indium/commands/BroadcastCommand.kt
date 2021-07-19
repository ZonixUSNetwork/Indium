package me.kansio.indium.commands

import cc.fyre.proton.command.Command
import cc.fyre.proton.command.param.Parameter
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.JsonBuilder
import org.bukkit.command.CommandSender


object BroadcastCommand {

    @JvmStatic
    @Command(
        names = ["broadcast"],
        permission = "rank.sradmin"
    )
    fun execute(sender: CommandSender, @Parameter(name = "message", wildcard = true) message: String) {
        IndiumPlugin.getInstance().publisher.write(Payload.BROADCAST, JsonBuilder().add("message", message).build())
    }

}