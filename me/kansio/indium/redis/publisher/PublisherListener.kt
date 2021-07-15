package me.kansio.indium.redis.publisher

import cc.fyre.venom.VenomAPI
import com.google.gson.JsonParser
import me.kansio.indium.utils.ServerUtil.sendToStaff
import org.redisson.api.listener.MessageListener
import me.kansio.indium.redis.Payload
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.utils.Pair
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
                ServerUtil.sendToStaff(message)
            }
            Payload.MESSAGE -> {
                val message = json["message"].asString
                ServerUtil.sendToStaff(message)
            }
            Payload.REQUEST -> {
                val server = json["server"].asString
                val message = json["message"].asString
                val sender = json["sender"].asString
                val senderUUID = UUID.fromString(json["uuid"].asString)
                val name = VenomAPI.instance.grantHandler.findBestRank(senderUUID).color.replace("&", "§") + sender

                val formattedMessage = "§9[Request] §b[$server] §f$sender §7has requested assistance: §f$message"

                ServerUtil.sendToStaff(formattedMessage)
            }

        }
    }
}