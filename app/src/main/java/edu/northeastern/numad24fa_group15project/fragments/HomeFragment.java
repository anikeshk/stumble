package edu.northeastern.numad24fa_group15project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.activities.MainActivity;
import edu.northeastern.numad24fa_group15project.adapters.HomeCarouselAdapter;
import edu.northeastern.numad24fa_group15project.adapters.HomeListAdapter;
import edu.northeastern.numad24fa_group15project.controllers.UserManager;
import edu.northeastern.numad24fa_group15project.models.Event;
import edu.northeastern.numad24fa_group15project.viewmodels.HomeViewModel;
import edu.northeastern.numad24fa_group15project.viewmodels.StumbleViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private StumbleViewModel viewModel;
    private HomeViewModel homeViewModel;

    private HomeCarouselAdapter homeCarouselAdapter;
    private RecyclerView carouselRecyclerView;

    private HomeListAdapter homeListAdapter;
    private RecyclerView listRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(StumbleViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        carouselRecyclerView = view.findViewById(R.id.carousel_recycler_view);
        carouselRecyclerView.setLayoutManager(new CarouselLayoutManager(new HeroCarouselStrategy()));

        homeCarouselAdapter = new HomeCarouselAdapter();
        carouselRecyclerView.setAdapter(homeCarouselAdapter);

        CarouselSnapHelper snapHelper = new CarouselSnapHelper();
        snapHelper.attachToRecyclerView(carouselRecyclerView);



        listRecyclerView = view.findViewById(R.id.list_recycler_view);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        homeListAdapter = new HomeListAdapter();
        listRecyclerView.setAdapter(homeListAdapter);

        viewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            homeListAdapter.setEvents(events);
        });
        viewModel.loadEvents();

        homeViewModel.getRecommendedEvents().observe(getViewLifecycleOwner(), events -> {
            homeCarouselAdapter.setEvents(events);
        });
        homeViewModel.loadRecommendedEvents();

        homeCarouselAdapter.setOnItemClickListener(event -> {
            openEventDetails(event);
        });

        homeListAdapter.setOnItemClickListener(event -> {
            openEventDetails(event);
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