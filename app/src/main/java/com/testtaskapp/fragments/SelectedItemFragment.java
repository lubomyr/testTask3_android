package com.testtaskapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.testtaskapp.R;
import com.testtaskapp.api.CategoriesApi;
import com.testtaskapp.api.RetrofitUtil;
import com.testtaskapp.databinding.FragmentSelectedBinding;
import com.testtaskapp.entities.SelectedItem;
import com.testtaskapp.repository.FeedsRepository;
import com.testtaskapp.repository.SelectedRepository;
import com.testtaskapp.utils.GsonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.testtaskapp.utils.KeyNames.KEY_ID;

public class SelectedItemFragment extends Fragment implements Callback<JsonElement> {
    private FragmentSelectedBinding binding;
    private CategoriesApi categoriesApi;
    private Call<JsonElement> call;
    private String TAG = "TestApp";
    private SelectedItem selectedItem;
    private long itemId;
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
        binding = FragmentSelectedBinding.inflate(inflater, container, false);

        bindFavoriteButtons();
        getItemId();

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (call != null)
            call.cancel();
    }

    private void initRetrofit() {
        Retrofit retrofit = RetrofitUtil.getSimpleRetrofit();
        categoriesApi = retrofit.create(CategoriesApi.class);
    }

    private void getItemId() {
        Bundle bundle = getArguments();
        if (bundle != null) {
           itemId = bundle.getLong(KEY_ID);
            getDataFromWeb(itemId);
        }
    }

    private void bindFavoriteButtons() {
        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.changeFavoriteState(itemId);
                refreshFavoriteView();
            }
        });
    }

    private void setViews() {
        if (selectedItem != null) {
            binding.setItem(selectedItem);
            Context context = getContext();
            if (context != null && selectedItem.getArtworkUrl100() != null)
                Glide.with(context).load(selectedItem.getArtworkUrl100()).into(binding.image);
            if (selectedItem.getDescription() != null)
                binding.description.setHtml(selectedItem.getDescription());
            refreshFavoriteView();
        }
    }

    private void refreshFavoriteView() {
        boolean status = FeedsRepository.isFavoriteById(itemId);
        int resource = status ? R.mipmap.favorite_active : R.mipmap.favorite_inactive;
        binding.favorite.setImageResource(resource);
    }

    private void getDataFromWeb(long itemID) {
        binding.progressBar.setVisibility(View.VISIBLE);
        call = categoriesApi.getItem(itemID);
        call.enqueue(this);
    }

    private void getDataFromDb() {
        selectedItem = SelectedRepository.getById(itemId);
        setViews();
    }

    @Override
    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        Log.d(TAG, "item onResponse");
        binding.progressBar.setVisibility(View.GONE);
        if (response.body() != null) {
            JsonElement json = response.body();
            JsonElement resultsJson = json.getAsJsonObject().get("results");
            JsonElement jsonItem = resultsJson.getAsJsonArray().get(0);
            Log.d(TAG, jsonItem.toString());
            selectedItem = GsonUtils.getGson().fromJson(jsonItem, new TypeToken<SelectedItem>() {
            }.getType());
            SelectedRepository.save(selectedItem);
            setViews();
        } else
            getDataFromDb();
    }

    @Override
    public void onFailure(Call<JsonElement> call, Throwable t) {
        Log.d(TAG, "item onFailure");
        binding.progressBar.setVisibility(View.GONE);
        getDataFromDb();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void changeFavoriteState(long itemId);
    }

}
