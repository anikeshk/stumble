package edu.northeastern.numad24fa_group15project.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.northeastern.numad24fa_group15project.models.User;

public class UserManager {
    private static UserManager instance;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private User currentUser;

    private UserManager() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void registerUser(String email, String password, final OnRegistrationListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();
                            currentUser = new User();
                            currentUser.setId(uid);
                            currentUser.setEmail(email);
                            db.collection("users").document(uid)
                                    .set(new HashMap<>())
                                    .addOnSuccessListener(aVoid -> {
                                        listener.onSuccess(currentUser);
                                    })
                                    .addOnFailureListener(e -> {
                                        listener.onFailure("User created but failed to initialize Firestore document: " + e.getMessage());
                                    });
                        }
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void loginUser(String email, String password, final OnLoginListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            fetchUserData(firebaseUser.getUid(), listener);
                        }
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    private void fetchUserData(String uid, final OnLoginListener listener) {
        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        currentUser = documentSnapshot.toObject(User.class);
                        listener.onSuccess(currentUser);
                    } else {
                        listener.onFailure("User data not found");
                    }
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    public void logoutUser() {
        mAuth.signOut();
        currentUser = null;
    }

    public void updateUserData(Map<String, Object> data, final OnUserUpdateListener listener) {
        if (currentUser == null || currentUser.getId() == null) {
            listener.onFailure("No user logged in");
            return;
        }

        db.collection("users").document(currentUser.getId())
                .update(data)
                .addOnSuccessListener(aVoid -> {
                    currentUser.updateFromMap(data);
                    listener.onSuccess();
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public interface OnRegistrationListener {
        void onSuccess(User user);

        void onFailure(String error);
    }

    public interface OnLoginListener {
        void onSuccess(User user);

        void onFailure(String error);
    }

    public interface OnUserUpdateListener {
        void onSuccess();

        void onFailure(String error);
    }
}
