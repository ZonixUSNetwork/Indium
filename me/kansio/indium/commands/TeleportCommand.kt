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


class TeleportCommand : PluginCommand() {

    @Command(
        name = "tp",
        permission = "rank.staff"
    )
    override fun onCommand(command: CommandArgs?) {
        var sender = command!!.player;
        var args = command.args

        if (args.size != 1) {
            sender.sendMessage("§cUsage: /tp <player>")
            return
        }
        var target = Bukkit.getPlayer(args[0])
        if (target == null) {
            sender.sendMessage("§cPlayer not found .")
            return
        }
        var targetName = VenomAPI.instance.grantHandler.findBestRank(target.uniqueId).color.replace("&", "§") + target.name
        sender.sendMessage("§6Teleporting you to §f$targetName§6.")
        sender.teleport(target)
    }
}

/*/

fuck you qlib
object TeleportCommand {



    @JvmStatic
    @Command(
        names = ["teleport", "tp"],
        permission = "rank.staff",
    )
    fun execute(sender: CommandSender, @Parameter(name = "player") target: Player) {
        val senderPlayer = sender as Player
        sender.sendMessage("§6Teleporting you to §f" + target.displayName  + "§6.")
        //senderPlayer.teleport(player)
    }

    @JvmStatic
    @Command(
        names = ["tphere", "s"],
        permission = "indium.command.tphere",
    )
    fun teleportHere(sender: CommandSender, @Parameter(name = "player") target: Player) {
        val senderPlayer = sender as Player
        sender.sendMessage("§6Teleporting §f" + target.displayName + " §6to you.")
        target.sendMessage("§6Teleporting you to §f" + target.displayName + " §6.")
        target.teleport(senderPlayer)
    }

}/*/