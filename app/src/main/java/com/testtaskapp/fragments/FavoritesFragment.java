package com.testtaskapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testtaskapp.R;
import com.testtaskapp.adapters.CategoryAdapter;
import com.testtaskapp.databinding.FragmentFavoritesBinding;
import com.testtaskapp.entities.Category;
import com.testtaskapp.entities.FeedItem;

import java.util.Arrays;
import java.util.List;

import static com.testtaskapp.utils.KeyNames.KEY_AUDIOBOOKS;
import static com.testtaskapp.utils.KeyNames.KEY_MOVIES;
import static com.testtaskapp.utils.KeyNames.KEY_PODCASTS;

public class FavoritesFragment extends Fragment implements CategoryAdapter.Listener {
    private FragmentFavoritesBinding binding;
    private List<Category> categoryList;
    private CategoryAdapter adapter;
    private Listener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        categoryList = getCategoryList();
        bindFeedsAdapter();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        listener.bottomMenuStateFix(3);
    }

    List<Category> getCategoryList() {
        Category audiobook = new Category(KEY_AUDIOBOOKS);
        Category movies = new Category(KEY_MOVIES);
        Category podcasts = new Category(KEY_PODCASTS);
        return Arrays.asList(audiobook, movies, podcasts);
    }

    private void bindFeedsAdapter() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CategoryAdapter(getContext(), R.layout.item_category);
        adapter.setListener(this);
        adapter.setData(categoryList);
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(FeedItem item) {
        if (listener != null)
            listener.onItemClick(item);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onItemClick(FeedItem item);
        void bottomMenuStateFix(int itemNum);
    }
}
