package edu.northeastern.numad24fa_group15project.controllers;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.northeastern.numad24fa_group15project.models.Event;
import edu.northeastern.numad24fa_group15project.models.Ticket;
import edu.northeastern.numad24fa_group15project.models.User;

public class EventRepository {

    private static final String EVENTS_COLLECTION = "events";
    private static final String TICKETS_COLLECTION = "tickets";
    private static final String SAVED_COLLECTION = "saved";
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;
    private UserManager userManager;


    public EventRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.userManager = UserManager.getInstance();
    }

    public Task<QuerySnapshot> getAllEvents() {
        return db.collection(EVENTS_COLLECTION).get();
    }

    public Task<QuerySnapshot> getRecommendedEvents() {
        Query query = db.collection(EVENTS_COLLECTION)
                .whereEqualTo("isRecommended", true)
                .orderBy("dateTime", Query.Direction.ASCENDING);
        return query.get();
    }

    public Task<QuerySnapshot> getStumbleEvents() {
        Query query = db.collection(EVENTS_COLLECTION)
                .whereEqualTo("isRecommended", false)
                .limit(5)
                .orderBy("dateTime", Query.Direction.ASCENDING);
        return query.get();
    }

    public Task<QuerySnapshot> getTicketEvents() {
        //Log.v("cc", userManager.getCurrentUser().getEmail());
        return db.collection(TICKETS_COLLECTION)
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid())
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    List<String> eventIds = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Ticket ticket = document.toObject(Ticket.class);
                        eventIds.add(ticket.getEventId());
                    }
                    return db.collection(EVENTS_COLLECTION)
                            .whereIn(FieldPath.documentId(), eventIds)
                            .get();
                });
    }

    public Task<Void> bookTicket(String eventId) {
        DocumentReference newTicketRef = db.collection("tickets").document();
        Map<String, Object> ticket = new HashMap<>();
        ticket.put("eventId", eventId);
        ticket.put("userId", mAuth.getCurrentUser().getUid());
        ticket.put("createdAt", FieldValue.serverTimestamp());
        return newTicketRef.set(ticket);
    }
}
