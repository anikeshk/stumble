package edu.northeastern.numad24fa_group15project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.models.Event;

public class HomeCarouselAdapter extends RecyclerView.Adapter<HomeCarouselAdapter.EventViewHolder> {
    private List<Event> events = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_carousel, parent, false);
        return new HomeCarouselAdapter.EventViewHolder(view);
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
        private TextView eventDescription;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.carousel_image_view);
            eventTitle = itemView.findViewById(R.id.textView3);
            // = itemView.findViewById(R.id.stumble_event_organizer);
            //eventLocation = itemView.findViewById(R.id.eventLocation);
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
//            //eventLocation.setText(event.getLocation());
//            eventDescription.setText(event.getDescription());

//            Glide.with(itemView.getContext())
//                    .load(event.getImageUrl())
//                    .placeholder(R.drawable.placeholder_image)
//                    .error(R.drawable.error_image)
//                    .into(eventImage);
        }
    }
}
