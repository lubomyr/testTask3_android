package com.testtaskapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.testtaskapp.R;
import com.testtaskapp.adapters.FeedsAdapter;
import com.testtaskapp.api.CategoriesApi;
import com.testtaskapp.api.RetrofitUtil;
import com.testtaskapp.databinding.FragmentFeedlistBinding;
import com.testtaskapp.entities.FeedItem;
import com.testtaskapp.repository.FeedsRepository;
import com.testtaskapp.utils.GsonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.testtaskapp.utils.KeyNames.KEY_AUDIOBOOKS;
import static com.testtaskapp.utils.KeyNames.KEY_CATEGORY;
import static com.testtaskapp.utils.KeyNames.KEY_MOVIES;
import static com.testtaskapp.utils.KeyNames.KEY_PODCASTS;
import static com.testtaskapp.utils.KeyNames.KEY_STATE;

public class FeedListFragment extends Fragment implements Callback<JsonElement>, FeedsAdapter.OnItemClickListener {
    private FragmentFeedlistBinding binding;
    private CategoriesApi categoriesApi;
    private String category;
    private Call<JsonElement> call;
    private String TAG = "TestApp";
    private List<FeedItem> list;
    private FeedsAdapter feedsAdapter;
    private int state;
    private Listener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRetrofit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFeedlistBinding.inflate(inflater, container, false);
        getCategory();
        if (list == null) {
            bindFeedsAdapter();
            getDataFromWeb();
        } else {
            bindFeedsAdapter();
            feedsAdapter.setData(list);
            feedsAdapter.notifyDataSetChanged();
        }

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (call != null)
            call.cancel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STATE, state);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            state = savedInstanceState.getInt(KEY_STATE);
        }
    }

    private void initRetrofit() {
        Retrofit retrofit = RetrofitUtil.getRssRetrofit();
        categoriesApi = retrofit.create(CategoriesApi.class);
    }

    private void getCategory() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            category = bundle.getString(KEY_CATEGORY);
        }
        binding.title.setText(category);
    }


    private void getDataFromWeb() {
        switch(category) {
            case KEY_AUDIOBOOKS:
                call = categoriesApi.getAudioBooks();
                break;
            case KEY_MOVIES:
                call = categoriesApi.getMovies();
                break;
            case KEY_PODCASTS:
                call = categoriesApi.getPodcasts();
                break;
        }
        call.enqueue(this);
    }

    private void getDataFromDb() {
        list = FeedsRepository.getByCategory(category);
        feedsAdapter.setData(list);
        feedsAdapter.notifyDataSetChanged();
    }

    private void bindFeedsAdapter() {
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        feedsAdapter = new FeedsAdapter(getContext(), R.layout.item_feed);
        feedsAdapter.setOnItemClickListener(this);
        binding.recycler.setAdapter(feedsAdapter);
    }


    @Override
    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        Log.d(TAG, "feed list onResponse");
        if (response.body() != null) {
            JsonElement json = response.body();
            JsonElement feedJson = json.getAsJsonObject().get("feed");
            JsonElement resultsJson = feedJson.getAsJsonObject().get("results");
            list = GsonUtils.getGson().fromJson(resultsJson, new TypeToken<List<FeedItem>>() {
            }.getType());
            feedsAdapter.setData(list);
            feedsAdapter.notifyDataSetChanged();
            FeedsRepository.saveAll(list);
        } else
            getDataFromDb();
    }

    @Override
    public void onFailure(Call<JsonElement> call, Throwable t) {
        Log.d(TAG, "feed list onFailure");
        getDataFromDb();
    }

    @Override
    public void onItemClick(FeedItem item) {
        Log.d(TAG, "clicked id :" + item.getId());
        if (this.listener != null)
            listener.onItemClick(item);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onItemClick(FeedItem item);
    }
}
