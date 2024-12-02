package edu.northeastern.numad24fa_group15project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.activities.RegisterSecondActivity;
import edu.northeastern.numad24fa_group15project.controllers.EventRepository;
import edu.northeastern.numad24fa_group15project.models.Event;

public class EventDetailsFragment extends Fragment {
    private static final String ARG_EVENT = "event";
    private Event event;
    private String eventTitle;

    public static EventDetailsFragment newInstance(Event event) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(ARG_EVENT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_stumble_event, container, false);

        EventRepository eventRepository = new EventRepository();

        // Populate views with event details
        TextView titleView = view.findViewById(R.id.stumble_event_title);
        titleView.setText(event.getTitle());

        TextView desc = view.findViewById(R.id.stumble_event_description);
        desc.setText(event.getDescription());

        ImageView imageView = view.findViewById(R.id.stumble_event_image);
        Glide.with(getContext())
                    .load(event.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);

        Button registerButton = view.findViewById(R.id.stumble_event_register_button);
        registerButton.setOnClickListener(v -> {
            eventRepository.bookTicket(event.getId())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "Ticket booked!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {

                    });
        });

        return view;
    }
}