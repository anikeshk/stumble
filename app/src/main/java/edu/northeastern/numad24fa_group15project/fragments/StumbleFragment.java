package edu.northeastern.numad24fa_group15project.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.adapters.StumbleEventAdapter;
import edu.northeastern.numad24fa_group15project.viewmodels.StumbleViewModel;

public class StumbleFragment extends Fragment {
    private StumbleViewModel viewModel;
    private ViewPager2 viewPager;
    private StumbleEventAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stumble, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager);
        viewModel = new ViewModelProvider(this).get(StumbleViewModel.class);

        adapter = new StumbleEventAdapter();
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                if (position == adapter.getItemCount() - 1) {
//                    viewModel.loadMoreEvents();
//                }
            }
        });

        viewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            adapter.setEvents(events);
        });

        viewModel.loadEvents();
    }
}