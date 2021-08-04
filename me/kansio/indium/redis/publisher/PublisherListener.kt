package me.kansio.indium.redis.publisher

import cc.fyre.venom.VenomAPI
import com.google.gson.JsonParser
import me.kansio.indium.utils.ServerUtil.sendToStaff
import org.redisson.api.listener.MessageListener
import me.kansio.indium.redis.Payload
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.`object`.Report
import me.kansio.indium.utils.Pair
import me.kansio.indium.utils.ReportUtils
import java.util.UUID
import me.kansio.indium.utils.ServerUtil
import org.bukkit.Bukkit

class PublisherListener : MessageListener<Pair<Payload?, String?>> {

    private val instance = IndiumPlugin.getInstance()

    override fun onMessage(channel: String, msg: Pair<Payload?, String?>) {
        val json = JsonParser().parse(msg.y).asJsonObject
        when (msg.x) {
            Payload.STAFF_CHAT -> {
                val server = json["server"].asString
                var staffMessage = json["message"].asString
                staffMessage = staffMessage.replace("\\", "\\\\")
                staffMessage = staffMessage.replace("$", "\\$")
                val name = json["name"].asString
                val message = "§9[SC] §b[$server§b] §f$name§7: §f$staffMessage"
                for (player in Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("rank.staff")) {
                        player.sendMessage(message)
                    }
                }
            }
            Payload.MESSAGE -> {
                val message = json["message"].asString
                for (player in Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("rank.staff")) {
                        player.sendMessage(message)
                    }
                }
            }
            Payload.REQUEST -> {
                val server = json["server"].asString
                val message = json["message"].asString
                val sender = json["sender"].asString

                val formattedMessage = "§9[Request] §b[$server] §f$sender §7has requested assistance: §f$message"

                for (player in Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("rank.staff")) {
                        player.sendMessage(formattedMessage)
                    }
                }
            }
            Payload.SEND_REPORT -> {
                val server = json["server"].asString
                val reported = json["reported"].asString
                val sender = json["reporter"].asString
                val reason = json["message"].asString
                val id = json["id"].asString

                val formattedMessage = "§9[Report] §b[$server] §f$sender §7has reported §f$reported§7 for §f$reason."
                IndiumPlugin.getInstance().reportManager.addReport(Report(reported, sender, reason, id))
                for (player in Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("rank.staff")) {
                        player.sendMessage(formattedMessage)
                    }
                }
            }
            Payload.FILTER -> {
                val server = json["server"].asString
                val sender = json["sender"].asString
                val message = json["message"].asString
                val senderUUID = UUID.fromString(json["uuid"].asString)
                val name = VenomAPI.instance.grantHandler.findBestRank(senderUUID).color.replace("&", "§") + sender

                val formattedMessage = "§c[Filtered] §7[$server] §f$name §7-> §f$message§7."

                for (player in Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("rank.staff")) {
                        player.sendMessage(formattedMessage)
                    }
                }
            }
            Payload.BROADCAST -> {
                val message = json["message"].asString
                Bukkit.broadcastMessage(message)
            }
            Payload.HANDLE_REPORT -> {
                val id = json["id"].asString
                IndiumPlugin.getInstance().reportManager.reports.remove(ReportUtils.findReport(id))
            }
        }
    }
}