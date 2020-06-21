package ru.basejava.webapp.model;

public class Institution {

    private String name;
    private String startDate;
    private String endDate;
    private String title;
    private String description;

    public Institution(String name, String startDate, String endDate, String title, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return  name + "\n"
                + startDate + " - " + endDate + "   " + title + "\n"
                + "\t" + "\t" + "\t" + "\t" + "\t" + description;
    }
}
