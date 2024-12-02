package edu.northeastern.numad24fa_group15project.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.activities.MainActivity;
import edu.northeastern.numad24fa_group15project.adapters.HomeListAdapter;
import edu.northeastern.numad24fa_group15project.adapters.TicketListAdapter;
import edu.northeastern.numad24fa_group15project.controllers.UserManager;
import edu.northeastern.numad24fa_group15project.models.Event;
import edu.northeastern.numad24fa_group15project.viewmodels.MyEventsViewModel;
import edu.northeastern.numad24fa_group15project.viewmodels.StumbleViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventsFragment extends Fragment {
    private MyEventsViewModel myEventsViewModel;

    private TicketListAdapter ticketListAdapter;
    private RecyclerView myEventsRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("MYEventsssssssssssssssxxccvb", "meow");
        myEventsViewModel = new ViewModelProvider(this).get(MyEventsViewModel.class);

        myEventsRecyclerView = view.findViewById(R.id.my_events_recycler_view);
        myEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ticketListAdapter = new TicketListAdapter();
        myEventsRecyclerView.setAdapter(ticketListAdapter);

        myEventsViewModel.getTicketEvents().observe(getViewLifecycleOwner(), events -> {
            Log.v("MYEventsssssssssss", events.toString());
            ticketListAdapter.setEvents(events);
        });

        myEventsViewModel.loadTicketEvents();

        ticketListAdapter.setOnItemClickListener(event -> {
            openEventDetails(event);
        });

        MaterialToolbar toolbar = view.findViewById(R.id.my_events_tool_bar);
        toolbar.setOnMenuItemClickListener(menuItem -> {

            if (menuItem.getItemId() == R.id.top_bar_logout) {
                logoutUser();
                return true;
            } else {
                return false;
            }
        });


    }

    private void logoutUser() {
        UserManager.getInstance().logoutUser();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void openEventDetails(Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_event_image, null);

        ImageView imageView = dialogView.findViewById(R.id.dialog_image_view);
        imageView.setImageResource(R.drawable.qrcode);

        builder.setView(dialogView);
        builder.setTitle(event.getTitle());
        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}