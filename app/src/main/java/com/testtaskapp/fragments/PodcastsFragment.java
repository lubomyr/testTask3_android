package com.testtaskapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testtaskapp.databinding.FragmentPodcastsBinding;

public class PodcastsFragment extends Fragment {
    private FragmentPodcastsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPodcastsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}
