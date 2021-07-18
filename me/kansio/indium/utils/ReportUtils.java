package me.kansio.indium.utils;

import me.kansio.indium.IndiumPlugin;
import me.kansio.indium.object.Report;
import org.bukkit.Bukkit;

public class ReportUtils {

    public static Report findReport(String id) {
        for(Report report : IndiumPlugin.getInstance().getReportManager().getReports()) {
            if(report.getId().equals(id)) {
                return report;
            }
        }
        return null;
    }

}
