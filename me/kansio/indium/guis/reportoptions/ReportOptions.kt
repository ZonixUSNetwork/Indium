package me.kansio.indium.guis.reportoptions

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
import org.bukkit.inventory.ItemStack

class ReportOptions : Listener {

    fun open(player: Player, reported: String) {
        var inventory = Bukkit.createInventory(null, 27, "§cReport §7- §f$reported")

        inventory.setItem(10 ,ItemBuilder(Material.EMERALD_BLOCK, 1).setName("§aAccept report").toItemStack())
        inventory.setItem(12 ,ItemBuilder(Material.REDSTONE_BLOCK, 1).setName("§cDeny report").toItemStack())
        inventory.setItem(14 ,ItemBuilder(Material.COMPASS, 1).setName("§aTeleport to reported").toItemStack())
        inventory.setItem(16 ,ItemBuilder(Material.PAPER, 1).setName("§aView chat history").toItemStack())

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

        var id = event.clickedInventory.name.replace("§cReport §7- §f", "")

        var currentItem = event.currentItem

        if (currentItem.type == Material.EMERALD_BLOCK) {
            val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has §aaccepted §7the report of " + ReportUtils.findReport(id).reported + "."
            player.closeInventory()
            Bukkit.getScheduler().runTaskLater(IndiumPlugin.getInstance(), { //you can't open inventories while in an inventory...
                IndiumPlugin.getInstance().punishmentGui.open(player, id)
            }, 3L)
            return
        }

        if (currentItem.type == Material.REDSTONE_BLOCK) {
            val message = "§9[Report] §f" + VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name + " §7has §cdenied §7the report of " + ReportUtils.findReport(id).reported + "."
            IndiumPlugin.getInstance().publisher.write(Payload.MESSAGE, JsonBuilder().add("message", message).build())
            IndiumPlugin.getInstance().publisher.write(Payload.HANDLE_REPORT, JsonBuilder().add("id", id).build())
        }

        if (currentItem.type == Material.COMPASS) {
            player.chat("/tp " + ReportUtils.findReport(id).reported)
        }

        if (currentItem.type == Material.PAPER) {
            player.chat("/co l " + ReportUtils.findReport(id).reported + " a:chat")
        }

        player.closeInventory()

    }

}