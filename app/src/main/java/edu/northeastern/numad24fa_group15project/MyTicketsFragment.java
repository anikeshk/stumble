package edu.northeastern.numad24fa_group15project;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import edu.northeastern.numad24fa_group15project.adapters.HomeCarouselAdapter;
import edu.northeastern.numad24fa_group15project.adapters.HomeListAdapter;
import edu.northeastern.numad24fa_group15project.controllers.UserManager;
import edu.northeastern.numad24fa_group15project.models.Event;
import edu.northeastern.numad24fa_group15project.views.StumbleViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTicketsFragment extends Fragment {
    private StumbleViewModel viewModel;
    private TabLayout tabLayout;

    private HomeListAdapter homeListAdapter;
    private RecyclerView myEventsRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_tickets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(StumbleViewModel.class);


        myEventsRecyclerView = view.findViewById(R.id.my_events_recycler_view);
        myEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        homeListAdapter = new HomeListAdapter();
        myEventsRecyclerView.setAdapter(homeListAdapter);

        viewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            homeListAdapter.setEvents(events);
        });
        viewModel.loadEvents();

        homeListAdapter.setOnItemClickListener(event -> {
            openEventDetails(event);
        });

        tabLayout = view.findViewById(R.id.my_events_tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0 ) {
//                    viewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
//                        homeListAdapter.setEvents(events);
//                    });
                    Log.v("ME", "MyEvents:Tickets!!!!!!!!!!!");
                } else {
//                    viewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
//                        events.clear();
//                        homeListAdapter.setEvents(events);
//                    });
                    Log.v("ME", "MyEvents:Saved!!!!!!!!!!!");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    private void logoutUser() {
        UserManager.getInstance().logoutUser();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void openEventDetails(Event event) {
        EventDetailsFragment detailsFragment = EventDetailsFragment.newInstance(event);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailsFragment)
                .addToBackStack(null)
                .commit();
    }
}