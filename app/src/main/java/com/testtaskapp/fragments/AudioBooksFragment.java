package com.testtaskapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testtaskapp.databinding.FragmentAudiobooksBinding;

public class AudioBooksFragment extends Fragment {
    private FragmentAudiobooksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAudiobooksBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}
