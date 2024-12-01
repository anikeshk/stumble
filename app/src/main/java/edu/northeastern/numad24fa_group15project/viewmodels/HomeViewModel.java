package edu.northeastern.numad24fa_group15project.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.northeastern.numad24fa_group15project.controllers.EventRepository;
import edu.northeastern.numad24fa_group15project.models.Event;

public class HomeViewModel extends ViewModel {
    private final EventRepository eventRepository;
    private final MutableLiveData<List<Event>> recommendedEvents = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingRecEvents = new MutableLiveData<>(false);
    private final MutableLiveData<List<Event>> listEvents = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingListEvents = new MutableLiveData<>(false);

    public HomeViewModel() {
        eventRepository = new EventRepository();
    }

    public LiveData<List<Event>> getRecommendedEvents() {
        return recommendedEvents;
    }

    public LiveData<List<Event>> getListEvents() {
        return listEvents;
    }

    public LiveData<Boolean> getIsLoadingRecEvents() {
        return isLoadingRecEvents;
    }

    public LiveData<Boolean> getIsLoadingListEvents() {
        return isLoadingRecEvents;
    }

    public void loadRecommendedEvents() {
        isLoadingRecEvents.setValue(true);
        eventRepository.getRecommendedEvents()
                .addOnSuccessListener(querySnapshot -> {
                    List<Event> eventList = querySnapshot.toObjects(Event.class);
                    Log.v("XXXXjirivbrebivj", eventList.toString());
                    recommendedEvents.setValue(eventList);
                    isLoadingRecEvents.setValue(false);
                })
                .addOnFailureListener(e -> {
                    isLoadingRecEvents.setValue(false);
                });
    }
}
