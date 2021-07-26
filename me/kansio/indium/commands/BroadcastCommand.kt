package me.kansio.indium.commands

import cc.fyre.proton.command.Command
import cc.fyre.proton.command.param.Parameter
import cc.fyre.venom.VenomAPI
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.JsonBuilder
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player


object BroadcastCommand {

    @JvmStatic
    @Command(
        names = ["broadcast", "say", "me"],
        permission = "rank.sradmin"
    )
    fun execute(sender: CommandSender, @Parameter(name = "message", wildcard = true) message: String) {
        if (sender !is Player) {
            IndiumPlugin.getInstance().publisher.write(Payload.BROADCAST, JsonBuilder().add("message", message).build())
        } else {
            var player = sender
            val message = " \n" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).prefix.replace("&", "§") + player.name + "§f: §c" + message + "\n "
            IndiumPlugin.getInstance().publisher.write(Payload.BROADCAST, JsonBuilder().add("message", message).build())
            player.sendMessage("§aYou've sent a message on all servers...")
        }
    }

}