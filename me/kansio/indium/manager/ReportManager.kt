package me.kansio.indium.manager

import me.kansio.indium.`object`.Report
import org.bukkit.entity.Player

class ReportManager {

    val reports: ArrayList<Report> = ArrayList()

    fun addReport(report: Report) {
        reports.add(report)
    }

    fun removeReport(report: Report) {
        reports.remove(report)
    }

}