package me.kansio.indium.commands

import cc.fyre.proton.command.Command
import cc.fyre.proton.command.param.Parameter
import cc.fyre.venom.VenomAPI
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.JsonBuilder
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object StaffChatCommand {

    @JvmStatic
    @Command(
        names = ["staffchat", "sc", "tsc"],
        permission = "rank.staff"
    )
    fun execute(sender: Player, @Parameter(name = "sc", wildcard = true, defaultValue = "sc_toggle") messages: String) {
        if (messages == "sc_toggle") {
            IndiumPlugin.getInstance().staffChatManager.toggle(sender)
            sender.sendMessage(
                if (IndiumPlugin.getInstance().staffChatManager.isToggled(sender))
                    "§aYour staff chat is now toggled."
                else
                    "§c§aYour staff chat is no longer toggled."
            );
            return
        }
        IndiumPlugin.getInstance().publisher.write(Payload.STAFF_CHAT, JsonBuilder()
            .add("server", IndiumPlugin.getInstance().serverName)
            .add("uuid", sender.uniqueId).add("message", messages)
            .add("name", VenomAPI.instance.grantHandler.findBestRank(sender.uniqueId).color.replace("&", "§") + sender.name)
            .build());
    }
}