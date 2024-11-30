package edu.northeastern.numad24fa_group15project.models;

import com.google.firebase.Timestamp;

public class Ticket {
    private String id;
    private String userId;
    private String eventId;
    private Timestamp createdDate;
    private TicketStatus status;

    public enum TicketStatus {
        UPCOMING,
        PAST,
        SAVED
    }

    public Ticket() {}
}
