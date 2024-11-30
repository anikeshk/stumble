package edu.northeastern.numad24fa_group15project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
    public HomeListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);
        return new HomeListAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeListAdapter.EventViewHolder holder, int position) {
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
            //eventDescription = itemView.findViewById(R.id.stumble_event_description);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(events.get(position));
                }
            });
        }

        void bind(Event event) {
            eventTitle.setText(event.getTitle());
            eventImage.setImageResource(R.drawable.event_demo);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
//            String formattedDate = dateFormat.format(event.getDateTime().toDate());
//            eventDate.setText(formattedDate);
//
            eventLocation.setText(event.getLocation());
            eventOrganizer.setText(event.getOrganizer());

//            Glide.with(itemView.getContext())
//                    .load(event.getImageUrl())
//                    .placeholder(R.drawable.placeholder_image)
//                    .error(R.drawable.error_image)
//                    .into(eventImage);
        }
    }
}
