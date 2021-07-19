package me.kansio.indium.commands

import cc.fyre.proton.command.param.Parameter
import cc.fyre.venom.VenomAPI
import me.kansio.indium.utils.PluginCommand
import me.kansio.indium.utils.UUIDUtils
import me.kansio.indium.utils.commandframework.Command
import me.kansio.indium.utils.commandframework.CommandArgs
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*


class TeleportHereCommand : PluginCommand() {

    @Command(
        name = "tphere",
        permission = "rank.mod"
    )
    override fun onCommand(command: CommandArgs?) {
        var sender = command!!.player;
        var args = command.args

        if (args.size != 1) {
            sender.sendMessage("§cUsage: /tphere <player>")
            return
        }
        var target = Bukkit.getPlayer(args[0])
        if (target == null) {
            sender.sendMessage("§cPlayer not found.")
            return
        }
        var targetName = VenomAPI.instance.grantHandler.findBestRank(target.uniqueId).color.replace("&", "§") + target.name
        sender.sendMessage("§6Teleporting §f$targetName §6to you.")
        target.sendMessage("§6Teleporting you to §f$targetName§6.")
        target.teleport(sender)
    }
}