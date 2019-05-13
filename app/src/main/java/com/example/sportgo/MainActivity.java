package com.example.sportgo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , StopSportFragment.StopSportFragmentListener
//        , SportFragment.SportFragmentListener
{


    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, new SportFragment());
        fragmentTransaction.commit();




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SportFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_sport);
        }

//        sportFragment = new SportFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, new StopSportFragment())
//                .replace(R.id.fragment_container, new SportFragment())
//                .commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
//        if (fragment instanceof StopSportFragment) {
//            StopSportFragment stopSportFragment = (StopSportFragment) fragment;
//            stopSportFragment.setStopSportFragmentListener(this);
//        }
    }

    @Override
    public void onInputASent(CharSequence input) {
        SportFragment sportFragment = (SportFragment) getSupportFragmentManager().findFragmentById(R.id.viewExerciseTime);
        if (sportFragment != null) {
            sportFragment.updateExerciseTime(input);
        } else {
            SportFragment newSportFragment = new SportFragment();
            Bundle args = new Bundle();
            args.putString("ExerciseTime", input.toString());
            newSportFragment.setArguments(args);
        }
    }
//
//    @Override
//    public void onInputBSent(CharSequence input , TextView textView) {
//        viewExerciseTime = textView;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_sport:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SportFragment()).commit();
                break;
            case R.id.nav_sport_record:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SportRecorderFragment()).commit();
                break;
            case R.id.nav_suggestion:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SportSuggestionFragment()).commit();
                break;
            case R.id.nav_diet_record:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DietFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_reporter:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReporterFragment()).commit();
                break;
            case R.id.nav_log_out:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LogoutFragment()).commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
