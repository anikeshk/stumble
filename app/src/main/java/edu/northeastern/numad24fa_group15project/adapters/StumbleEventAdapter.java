package edu.northeastern.numad24fa_group15project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.controllers.EventRepository;
import edu.northeastern.numad24fa_group15project.models.Event;
public class StumbleEventAdapter extends RecyclerView.Adapter<StumbleEventAdapter.EventViewHolder> {
    private List<Event> events = new ArrayList<>();

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stumble_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private ImageView eventImage;
        private TextView eventTitle;
        private TextView eventDate;
        private TextView eventLocation;
        private TextView eventOrganizer;
        private TextView eventDescription;
        private ChipGroup interestsChipGroup;
        private Button registerButton;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.stumble_event_image);
            eventTitle = itemView.findViewById(R.id.stumble_event_title);
            eventDate = itemView.findViewById(R.id.stumble_event_date);
            eventLocation = itemView.findViewById(R.id.stumble_event_location);
            eventOrganizer = itemView.findViewById(R.id.stumble_event_organizer);
            eventDescription = itemView.findViewById(R.id.stumble_event_description);
            interestsChipGroup = itemView.findViewById(R.id.stumble_event_interests);
            registerButton = itemView.findViewById(R.id.stumble_event_register_button);
        }

        void bind(Event event) {
            EventRepository eventRepository = new EventRepository();
            eventTitle.setText(event.getTitle());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(event.getDateTime().toDate());
            eventDate.setText(formattedDate);

            eventOrganizer.setText(event.getOrganizer());
            eventLocation.setText(event.getLocation());
            eventDescription.setText(event.getDescription());

            interestsChipGroup.removeAllViews();
            for (String interest : event.getInterests()) {
                Chip chip = new Chip(itemView.getContext());
                chip.setText(interest);
                interestsChipGroup.addView(chip);
            }
            Glide.with(itemView.getContext())
                    .load(event.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(eventImage);

            registerButton.setOnClickListener(v -> {
                eventRepository.bookTicket(event.getId())
                        .addOnSuccessListener(aVoid -> {
                            Snackbar.make(v.getRootView(), "RSVP confirmed!", Snackbar.LENGTH_SHORT)
                                    .show();
                        })
                        .addOnFailureListener(e -> {

                        });
            });
        }
    }
}