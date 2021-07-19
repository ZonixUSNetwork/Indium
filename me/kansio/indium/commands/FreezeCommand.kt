package me.kansio.indium.commands

import cc.fyre.proton.command.Command
import cc.fyre.proton.command.param.Parameter
import cc.fyre.venom.VenomAPI
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.JsonBuilder
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object FreezeCommand {

    @JvmStatic
    @Command(
        names = ["freeze", "ss"],
        permission = "rank.staff"
    )
    fun execute(sender: Player, @Parameter(name = "player") player: Player) {
        if (!IndiumPlugin.getInstance().freezeManager.isFrozen(player)) {
            if (Bukkit.getServerName().contains("Practice")) player.health = 0.0
            player.teleport(player.world.getHighestBlockAt(player.location).location)
            IndiumPlugin.getInstance().freezeManager.addFrozen(player)
            IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", "§9[Staff] §b[${IndiumPlugin.getInstance().serverName}] §f" + VenomAPI.instance.grantHandler.findBestRank(sender.uniqueId).color.replace("&", "§") + sender.name + " §7froze §f" + player.displayName + "§7.").build())
            sender.sendMessage("§aYou've frozen ${player.name}")
            player.sendMessage("§4§m----------------------------------------------")
            player.sendMessage("§fYou have been §4§lFROZEN§e!")
            player.sendMessage("  §fDo not §c§lLOGOUT §for you will be banned.")
            player.sendMessage("  §fJoin teamspeak: §fts.zonix.us§f, you have 3 minutes.")
            player.sendMessage("§4§m----------------------------------------------")
        } else {
            player.sendMessage("§aYou are now unfrozen.")
            IndiumPlugin.getInstance().freezeManager.removeFrozen(player)
            IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", "§9[Staff] §b[${IndiumPlugin.getInstance().serverName}] §f" + VenomAPI.instance.grantHandler.findBestRank(sender.uniqueId).color.replace("&", "§") + sender.name + " §7unfroze §f" + player.displayName + "§7.").build())
        }

    }

}