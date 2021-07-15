package me.kansio.indium.commands

import cc.fyre.proton.command.param.Parameter
import cc.fyre.venom.VenomAPI
import me.kansio.indium.utils.PluginCommand
import me.kansio.indium.utils.UUIDUtils
import me.kansio.indium.utils.commandframework.Command
import me.kansio.indium.utils.commandframework.CommandArgs
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*


class TeleportPosCommand : PluginCommand() {

    @Command(
        name = "tppos",
        permission = "indium.command.tppos"
    )
    override fun onCommand(command: CommandArgs?) {
        var sender = command!!.player;
        var args = command.args

        if (args.size != 3) {
            sender.sendMessage("§cUsage: /tppos <x> <y> <z>")
            return
        }
        val x = args[0]
        val y = args[1]
        val z = args[2]
        sender.sendMessage("§6Teleporting to §f$x§6, §f$y§6, §f$z§6.")
        val location = Location(sender.world, x.toDouble(), y.toDouble(), z.toDouble())
        sender.teleport(location)
    }
}