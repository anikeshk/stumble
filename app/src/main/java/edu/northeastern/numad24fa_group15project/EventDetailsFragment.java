package edu.northeastern.numad24fa_group15project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.northeastern.numad24fa_group15project.models.Event;

public class EventDetailsFragment extends Fragment {
    private static final String ARG_EVENT = "event";
    private Event event;
    private String eventTitle;

    public static EventDetailsFragment newInstance(Event event) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        // maybe make parseable
        args.putString(ARG_EVENT, event.getTitle());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventTitle = getArguments().getString(ARG_EVENT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_stumble_event, container, false);

        // Populate views with event details
        TextView titleView = view.findViewById(R.id.stumble_event_title);
        titleView.setText(eventTitle);

//        // ... set other event details ...
//
//        Button registerButton = view.findViewById(R.id.register_button);
//        registerButton.setOnClickListener(v -> {
//            // Handle registration logic
//        });

        return view;
    }
}