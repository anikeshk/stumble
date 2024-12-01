package edu.northeastern.numad24fa_group15project.controllers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.northeastern.numad24fa_group15project.models.Event;
import edu.northeastern.numad24fa_group15project.models.Ticket;
import edu.northeastern.numad24fa_group15project.models.User;

public class EventRepository {

    private static final String EVENTS_COLLECTION = "events";
    private static final String TICKETS_COLLECTION = "tickets";
    private static final String SAVED_COLLECTION = "saved";
    private final FirebaseFirestore db;
    private User user;


    public EventRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.user = UserManager.getInstance().getCurrentUser();
    }

    public Task<QuerySnapshot> getAllEvents() {
        return db.collection(EVENTS_COLLECTION).get();
    }

    public Task<QuerySnapshot> getAllSavedEvents() {
        return db.collection(SAVED_COLLECTION).get();
    }

    public Task<QuerySnapshot> getRecommendedEvents() {
        Query query = db.collection(EVENTS_COLLECTION)
                .whereEqualTo("isRecommended", true);
        return query.get();
    }

}
