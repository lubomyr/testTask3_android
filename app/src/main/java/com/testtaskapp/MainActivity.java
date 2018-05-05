package com.testtaskapp;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.testtaskapp.databinding.ActivityMainBinding;
import com.testtaskapp.entities.FeedItem;
import com.testtaskapp.fragments.FavoritesFragment;
import com.testtaskapp.fragments.FeedListFragment;
import com.testtaskapp.fragments.SelectedItemFragment;
import com.testtaskapp.repository.FeedsRepository;

import static com.testtaskapp.utils.KeyNames.KEY_AUDIOBOOKS;
import static com.testtaskapp.utils.KeyNames.KEY_CATEGORY;
import static com.testtaskapp.utils.KeyNames.KEY_FAVORITES;
import static com.testtaskapp.utils.KeyNames.KEY_ID;
import static com.testtaskapp.utils.KeyNames.KEY_MOVIES;
import static com.testtaskapp.utils.KeyNames.KEY_PODCASTS;
import static com.testtaskapp.utils.KeyNames.KEY_SELECTED;

public class MainActivity extends AppCompatActivity  implements FeedListFragment.Listener,
        SelectedItemFragment.Listener {
    private ActivityMainBinding binding;
    private String TAG = "TestApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        bindingBottomMenu();
        addFeedListFragment(KEY_AUDIOBOOKS);
    }

    private void bindingBottomMenu() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_audiobooks:
                                addFeedListFragment(KEY_AUDIOBOOKS);
                                break;
                            case R.id.item_movies:
                                addFeedListFragment(KEY_MOVIES);
                                break;
                            case R.id.item_podcasts:
                                addFeedListFragment(KEY_PODCASTS);
                                break;
                            case R.id.item_favorites:
                                addFavoritesFragment();
                                break;
                        }
                        return true;
                    }
                });
    }

    private void addFragment(Fragment fragment, boolean isAddToBackStack, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentsFrame, fragment, tag);

        if (isAddToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }

    private void removeFragmentFromStack(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commit();
        manager.popBackStack();
    }

    private void addFeedListFragment(String category) {
        FeedListFragment feedListFragment = new FeedListFragment();
        feedListFragment.setListener(this);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATEGORY, category);
        feedListFragment.setArguments(bundle);
        addFragment(feedListFragment, true, KEY_AUDIOBOOKS);
    }

    private void addFavoritesFragment() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        //favoritesFragment.setListener(this);
        addFragment(favoritesFragment, true, KEY_FAVORITES);
    }

    private void addSelectedItemFragment(FeedItem item) {
        SelectedItemFragment selectedItemFragment = new SelectedItemFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(KEY_ID, item.getId());
        selectedItemFragment.setArguments(bundle);
        selectedItemFragment.setListener(this);
        addFragment(selectedItemFragment, true, KEY_SELECTED);
    }

    @Override
    public void onItemClick(FeedItem item) {
        Log.d(TAG, "clicked id :" + item.getId());
        addSelectedItemFragment(item);
    }

    @Override
    public void changeFavoriteState(long itemId) {
        Log.d(TAG, "clicked favorite id :" + itemId);
        FeedItem item = FeedsRepository.getById(itemId);
        Log.d(TAG, "current state :" + item.getIsFavorite());
        item.setIsFavorite(!item.getIsFavorite());
        FeedsRepository.update(item);
    }
}
