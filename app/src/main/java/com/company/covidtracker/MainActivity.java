package com.company.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.company.covidtracker.Model.ContactModel.ContactRoot;
import com.company.covidtracker.Model.ContactModel.Primary;
import com.company.covidtracker.Model.ContactModel.Regional;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentTransaction fragmentTransaction;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MenuItem prevItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        phoneNumber = findViewById(R.id.contact_primary_number);
//        tollFreeNumber = findViewById(R.id.contact_primary_toll_free);
//        emailAddress = findViewById(R.id.contact_primary_email);
//        facebookAddr = findViewById(R.id.contact_primary_fb);
//        twitterAddr = findViewById(R.id.contact_primary_twitter);
//        mediaLink = findViewById(R.id.contact_primary_media);
//
//        regionalContactsTable = findViewById(R.id.contacts_regional);

//        getContactDetails();

        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("Covid-19 Tracker");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.main_drawer_layout);
        actionBarDrawerToggle = setupDrawerToggle();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, new ContactsFragment());
        fragmentTransaction.commit();

        navigationView = findViewById(R.id.main_nav_view);

        prevItem = navigationView.getMenu().findItem(R.id.nav_contacts);
        prevItem.setChecked(true);
        toolbar.setTitle(prevItem.getTitle());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                prevItem.setChecked(false);
                prevItem = menuItem;
                if (menuItem.getTitle().equals(toolbar.getTitle())) {
                    drawerLayout.closeDrawers();
                    return true;
                }

                if (menuItem.getItemId() == R.id.nav_contacts) {
                    fragmentManager.beginTransaction().replace(R.id.main_frame_layout, new ContactsFragment()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.nav_notification) {
                    fragmentManager.beginTransaction().replace(R.id.main_frame_layout, new NotificationFragment()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.nav_hospital_dashboards) {
                    fragmentManager.beginTransaction().replace(R.id.main_frame_layout, new HospitalBedsFragment()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.nav_medical_colleges) {
                    fragmentManager.beginTransaction().replace(R.id.main_frame_layout, new MedicalCollegeFragment()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.nav_deceased) {
                    fragmentManager.beginTransaction().replace(R.id.main_frame_layout, new DeceasedFragment()).commit();
                    menuItem.setChecked(true);
                    toolbar.setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true;
        switch (item.getItemId()) {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
