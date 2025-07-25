package com.example.myphantom;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navHostFragment = findViewById(R.id.nav_host_fragment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            String tag = "";

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                tag = "HOME";
            } else if (itemId == R.id.nav_feed) {
                selectedFragment = new FeedFragment();
                tag = "FEED";
            } else if (itemId == R.id.nav_trending) {
                selectedFragment = new SwapFragment();
                tag = "TRENDING";
            } else if (itemId == R.id.nav_history) {
                selectedFragment = new HistoryFragment();
                tag = "HISTORY";
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
                tag = "SEARCH";
            }
            if (selectedFragment != null) {
                loadFragment(selectedFragment, tag);
                return true;
            }
            return false;
        });
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
        );

        fragmentTransaction.replace(R.id.nav_host_fragment, fragment, tag);
        fragmentTransaction.commit();
    }
    @Override
    public void onNavigateToFragment(int menuItemId) {
        // When HomeFragment tells us to navigate, set the selected item in BottomNavigationView
        bottomNavigationView.setSelectedItemId(menuItemId);
    }
}