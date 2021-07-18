package me.kansio.indium.utils

import me.kansio.indium.IndiumPlugin
import org.bukkit.entity.Player
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException

object BungeeUtils {


    fun send(pl: Player, server: String?) {
        val b = ByteArrayOutputStream()
        val out = DataOutputStream(b)
        try {
            out.writeUTF("Connect")
            out.writeUTF(server)
        } catch (localIOException: IOException) {
        }
        pl.sendPluginMessage(IndiumPlugin.getInstance(), "BungeeCord", b.toByteArray())
    }
}