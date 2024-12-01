package edu.northeastern.numad24fa_group15project.models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Ticket {
    private String id;
    private String userId;
    private String eventId;
    private Timestamp createdAt;

    public Ticket() {}

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
