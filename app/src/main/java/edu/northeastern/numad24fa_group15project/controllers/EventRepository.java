package edu.northeastern.numad24fa_group15project.controllers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad24fa_group15project.models.Ticket;

public class EventRepository {

    private static final String EVENTS_COLLECTION = "events";
    private static final String TICKETS_COLLECTION = "tickets";
    private static final String SAVED_COLLECTION = "saved";
    private final FirebaseFirestore db;


    public EventRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<QuerySnapshot> getAllEvents() {
        return db.collection(EVENTS_COLLECTION).get();
    }

    public Task<QuerySnapshot> getAllSavedEvents() {
        return db.collection(SAVED_COLLECTION).get();
    }

    public Task<QuerySnapshot> getAllTickets() {
        Task<QuerySnapshot> ticketsTask = db.collection("tickets")
                //.whereEqualTo("userId", userId)
                .get();


        return ticketsTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            List<String> eventIds = new ArrayList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                Ticket ticket = document.toObject(Ticket.class);
                eventIds.add(ticket.getEventId());
            }

            // Query events using the collected eventIds
            return db.collection("events")
                    .whereIn(FieldPath.documentId(), eventIds)
                    .get();
        });
    }




//    make this book ticket/save event
//    public Task<Void> createUserEvent(Ticket ticket) {
//        return db.collection(TICKETS_COLLECTION).document(ticket.getId()).set(ticket);
//    }

}
