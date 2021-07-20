package me.kansio.indium.guis.punishments

import cc.fyre.venom.VenomAPI
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.ItemBuilder
import me.kansio.indium.utils.JsonBuilder
import me.kansio.indium.utils.ReportUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class PunishmentGui : Listener {

    fun open(player: Player, reported: String) {
        var inventory = Bukkit.createInventory(null, 27, "§cReport §7- §f$reported")
        inventory.setItem(8 , ItemBuilder(Material.CARPET, 1).setName("§cSkip").toItemStack())
        inventory.setItem(9 , ItemBuilder(Material.IRON_SWORD, 1).setName("§cCheating").toItemStack())
        inventory.setItem(10 , ItemBuilder(Material.ICE, 1).setName("§cFreezing Minecraft").toItemStack())
        inventory.setItem(11 , ItemBuilder(Material.LEASH, 1).setName("§cRacism").toItemStack())
        inventory.setItem(12 , ItemBuilder(Material.PAPER, 1).setName("§cSpamming").toItemStack())
        inventory.setItem(13 , ItemBuilder(Material.WATCH, 1).setName("§cEncouraging Self Harm").toItemStack())
        inventory.setItem(14 , ItemBuilder(Material.DIRT, 1).setName("§cCamping").toItemStack())
        inventory.setItem(15 , ItemBuilder(Material.BRICK, 1).setName("§cMedia Advertisement").toItemStack())
        inventory.setItem(16 , ItemBuilder(Material.BOOK, 1).setName("§cInappropriate Links").toItemStack())
        inventory.setItem(17 , ItemBuilder(Material.NAME_TAG, 1).setName("§cInappropriate Name").toItemStack())
        player.openInventory(inventory)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.clickedInventory == null) {
            return
        }
        if (!event.clickedInventory.name.contains("§cReport §7-")) {
            return
        }
        event.isCancelled = true
        if (event.whoClicked !is Player) {
            return
        }
        var player = event.whoClicked as Player

        if (event.currentItem == null) {
            return
        }

        if (event.currentItem.itemMeta == null) {
            return
        }

        var id = event.clickedInventory.name.replace("§cReport §7- §f", "")
        var reported = ReportUtils.findReport(id).reported

        var currentItem = event.currentItem
        var reason = currentItem.itemMeta.displayName.replace("§c", "")

        when (reason) {
            "Skip" -> {
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Cheating" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "ban $reported perm Cheating")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Freezing Minecraft" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "warn $reported Freezing Minecraft")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Racism" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "mute $reported 60d Discrimination")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Spamming" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "mute $reported 30m Spamming")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Encouraging Self Harm" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "mute $reported perm Encouraging Self Harm")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Camping" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "kick $reported Camping")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Media Advertisement" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "warn $reported Media Advertisement")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Inappropriate Links" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "mute $reported 14d Inappropriate Links")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
            "Inappropriate Name" -> {
                val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has punished §f" + ReportUtils.findReport(id).reported + " §7for §c$reason."
                IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
                Bukkit.dispatchCommand(player, "ban $reported perm Inappropriate Name - Please change and appeal.")
                IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
            }
        }
        player.closeInventory()

    }

}