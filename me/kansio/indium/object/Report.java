package me.kansio.indium.object;

public class Report {

    private String reported;
    private String reporter;
    private String reason;
    private String id;

    public String getReason() {
        return reason;
    }

    public String getReporter() {
        return reporter;
    }

    public String getReported() {
        return reported;
    }

    public String getId() {
        return id;
    }

    public Report(String reportedPlayer, String report, String reason, String id) {
        this.reason = reason;
        this.reporter = report;
        this.reported = reportedPlayer;
        this.id = id;

    }
}
