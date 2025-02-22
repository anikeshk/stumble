package edu.northeastern.numad24fa_group15project.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.northeastern.numad24fa_group15project.controllers.EventRepository;
import edu.northeastern.numad24fa_group15project.models.Event;

public class StumbleViewModel extends ViewModel {
    private final EventRepository eventRepository;
    private final MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public StumbleViewModel() {
        eventRepository = new EventRepository();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadEvents() {
        isLoading.setValue(true);
        eventRepository.getStumbleEvents()
                .addOnSuccessListener(querySnapshot -> {
                    List<Event> eventList = querySnapshot.toObjects(Event.class);
                    Log.v("MEGGGGGGGG", eventList.toString());
                    events.setValue(eventList);
                    isLoading.setValue(false);
                })
                .addOnFailureListener(e -> {
                    isLoading.setValue(false);
                });
    }
}
