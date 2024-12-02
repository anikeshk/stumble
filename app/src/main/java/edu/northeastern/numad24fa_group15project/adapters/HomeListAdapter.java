package edu.northeastern.numad24fa_group15project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.models.Event;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.EventViewHolder>{
    private List<Event> events = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public void setOnItemClickListener(HomeListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);
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

    class EventViewHolder extends RecyclerView.ViewHolder {
        private ImageView eventImage;
        private TextView eventTitle;
        private TextView eventDate;
        private TextView eventLocation;
        private TextView eventOrganizer;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.home_list_image);
            eventTitle = itemView.findViewById(R.id.home_list_title);
            eventOrganizer = itemView.findViewById(R.id.home_list_organizer);
            eventLocation = itemView.findViewById(R.id.home_list_location);
            eventDate = itemView.findViewById(R.id.home_list_date);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(events.get(position));
                }
            });
        }

        void bind(Event event) {
            eventTitle.setText(event.getTitle());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(event.getDateTime().toDate());
            eventDate.setText(formattedDate);

            eventLocation.setText(event.getLocation());
            eventOrganizer.setText(event.getOrganizer());
            Glide.with(itemView.getContext())
                    .load(event.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(eventImage);
        }
    }
}
