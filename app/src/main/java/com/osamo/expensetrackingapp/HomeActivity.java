package com.osamo.expensetrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private DashboardFragment dashboardFragment;
    private IncomeFragment incomeFragment;
    private ExpensesFragment expensesFragment;
    private GoalsFragment goalsFragment;
    private AnalyticsFragment analyticsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        frameLayout = findViewById(R.id.main_frame);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Track Your Budget");
        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //toggle.setHomeAsUpIndicator(R.drawable.baseline_drawer_icon_menu_24);

        toolbar.setNavigationIcon(R.drawable.baseline_drawer_icon_menu_24);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       dashboardFragment = new DashboardFragment();
        incomeFragment = new IncomeFragment();
        expensesFragment = new ExpensesFragment();
       goalsFragment = new GoalsFragment();
        analyticsFragment = new AnalyticsFragment();


        setFragment(dashboardFragment);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.dashboard:
                        setFragment(dashboardFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.purple_700);
                        return true;

                    case R.id.income:
                        setFragment(incomeFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.blue);
                        return true;

                    case R.id.expenses:
                        setFragment(expensesFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.red);
                        return true;

                    case R.id.goals:
                        setFragment(goalsFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.brown);
                        return true;

                    case R.id.analysis:
                        setFragment(analyticsFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.teal_200);
                        return true;


                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
    }}

    @Override
    public void onBackPressed() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_frame);
        if (currentFragment instanceof DashboardFragment) {
            // exit the application
            finishAffinity();
        } else {
            // go back to previous fragment
            super.onBackPressed();
        }


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
         // super.onBackPressed();

        }

    }

    public  void displaySelectedListener(int itemId){

        Fragment fragment = null;

        switch (itemId) {
            case R.id.dashboard:
                fragment = new DashboardFragment();
                break;

            case R.id.income2:
               fragment = new IncomeFragment();
                break;

            case R.id.expenses2:
               fragment = new ExpensesFragment();
                break;

            case R.id.goals2:
             fragment =   new GoalsFragment();
                break;

            case R.id.analysis2:
             fragment = new AnalyticsFragment();
                break;

            case R.id.logout:
                Toast.makeText(this, "Thank you for using this app to track your budget!", Toast.LENGTH_SHORT).show();
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishAffinity();
                    }
                },3000);
                break;


        }

        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame,fragment);
            ft.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListener(item.getItemId());
        return true;
    }


}