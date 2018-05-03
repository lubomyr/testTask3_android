package com.testtaskapp;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.testtaskapp.databinding.ActivityMainBinding;
import com.testtaskapp.fragments.AudioBooksFragment;
import com.testtaskapp.fragments.FavoritesFragment;
import com.testtaskapp.fragments.MoviesFragment;
import com.testtaskapp.fragments.PodcastsFragment;
import com.testtaskapp.fragments.SelectedItemFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String KEY_AUDIOBOOKS = "audiobooks";
    private final String KEY_MOVIES = "movies";
    private final String KEY_PODCASTS = "podcasts";
    private final String KEY_FAVORITES = "favorites";
    private final String KEY_SELECTED = "selected";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        bindingBottomMenu();
        addAudioBooksFragment();
    }

    private void bindingBottomMenu() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_audiobooks:
                                addAudioBooksFragment();
                                break;
                            case R.id.item_movies:
                                addMoviesFragment();
                                break;
                            case R.id.item_podcasts:
                                addPodcastsFragment();
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

    private void addAudioBooksFragment() {
        Fragment audioBooksFragment = new AudioBooksFragment();
        //audioBooksFragment.setListener(this);
        addFragment(audioBooksFragment, true, KEY_AUDIOBOOKS);
    }

    private void addMoviesFragment() {
        Fragment moviesFragment = new MoviesFragment();
        //moviesFragment.setListener(this);
        addFragment(moviesFragment, true, KEY_MOVIES);
    }

    private void addPodcastsFragment() {
        Fragment podcastsFragment = new PodcastsFragment();
        //podcastsFragment.setListener(this);
        addFragment(podcastsFragment, true, KEY_PODCASTS);
    }

    private void addFavoritesFragment() {
        Fragment favoritesFragment = new FavoritesFragment();
        //favoritesFragment.setListener(this);
        addFragment(favoritesFragment, true, KEY_FAVORITES);
    }

    private void addSelectedItemFragment() {
        Fragment selectedItemFragment = new SelectedItemFragment();
        //favoritesFragment.setListener(this);
        addFragment(selectedItemFragment, true, KEY_SELECTED);
    }

}
