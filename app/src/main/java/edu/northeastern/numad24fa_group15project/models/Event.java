package edu.northeastern.numad24fa_group15project.models;

import com.google.firebase.Timestamp;
import java.util.List;

public class Event {
    private String id;
    private String title;
    private String description;
    private Timestamp dateTime;
    private String location;
    private String imageUrl;
    private List<String> interests;
    private boolean isRecommended;
    private String organizer;

    public Event() {}

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getInterests() {
        return interests;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public String getOrganizer() {
        return organizer;
    }
}