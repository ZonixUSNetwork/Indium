package me.kansio.indium.guis.reportgui

import me.kansio.indium.IndiumPlugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class ReportsGui : Listener {



    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.clickedInventory == null) {
            return
        }
        if (!event.clickedInventory.name.contains("Reports")) {
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

        player.closeInventory()
        Bukkit.getScheduler().runTaskLater(IndiumPlugin.getInstance(), { //you can't open inventories while in an inventory...
            IndiumPlugin.getInstance().reportOptions.open(player, event.currentItem.itemMeta.displayName.replace("§c", ""))
        }, 3L)

    }

    fun open(staff: Player) {
        var inventory = Bukkit.createInventory(null, 27, "Reports")
        for (report in IndiumPlugin.getInstance().reportManager.reports) {
            val reportItem = me.kansio.indium.utils.ItemBuilder(Material.PAPER, 1).setName("§c" + report.id).setLore("§cReported: §f" + report.reported, "§cReporter: §f" + report.reporter, "§cReason: §f" + report.reason).toItemStack()
            inventory.addItem(reportItem)
        }
        staff.openInventory(inventory)
        //IndiumPlugin.getInstance().reportOptions.open(staff, "dsffsdfsd")
    }

}