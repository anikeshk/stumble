package edu.northeastern.numad24fa_group15project.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.northeastern.numad24fa_group15project.controllers.EventRepository;
import edu.northeastern.numad24fa_group15project.models.Event;

public class MyEventsViewModel extends ViewModel {

    private final EventRepository eventRepository;
    private final MutableLiveData<List<Event>> ticketEvents = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingTicketEvents = new MutableLiveData<>(false);

    public MyEventsViewModel() {
        eventRepository = new EventRepository();
    }

    public LiveData<List<Event>> getTicketEvents() {
        return ticketEvents;
    }

    public LiveData<Boolean> getIsLoadingTicketEvents() {
        return isLoadingTicketEvents;
    }

    public void loadTicketEvents() {
        isLoadingTicketEvents.setValue(true);
        eventRepository.getTicketEvents()
                .addOnSuccessListener(querySnapshot -> {
                    List<Event> eventList = querySnapshot.toObjects(Event.class);
                    Log.v("XXXXjirivbrebivj0snm|MyEvents|Tickets", eventList.toString());
                    ticketEvents.setValue(eventList);
                    isLoadingTicketEvents.setValue(false);
                })
                .addOnFailureListener(e -> {
                    isLoadingTicketEvents.setValue(false);
                });
    }
}
