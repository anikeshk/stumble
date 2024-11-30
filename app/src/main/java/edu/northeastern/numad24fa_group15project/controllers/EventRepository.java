package edu.northeastern.numad24fa_group15project.controllers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import edu.northeastern.numad24fa_group15project.models.Ticket;

public class EventRepository {

    private static final String EVENTS_COLLECTION = "events";
    private static final String TICKETS_COLLECTION = "tickets";
    private final FirebaseFirestore db;


    public EventRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<QuerySnapshot> getAllEvents() {
        return db.collection(EVENTS_COLLECTION).get();
    }

//    make this book ticket/save event
//    public Task<Void> createUserEvent(Ticket ticket) {
//        return db.collection(TICKETS_COLLECTION).document(ticket.getId()).set(ticket);
//    }

}
