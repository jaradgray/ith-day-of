package com.jgendeavors.ithdayof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.activity_main_recyclerview);
        // Every RecyclerView needs a LayoutManager. Our RecyclerView will display items
        // in a vertical list, so we need a LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); // improves performance when we know the size of our RecyclerView in the layout won't change
        // Every RecyclerView needs a RecyclerView.Adapter. We'll need a reference to it, too.
        final HolidayAdapter adapter = new HolidayAdapter();
        recyclerView.setAdapter(adapter);

        // Handle clicks on RecyclerView items by implementing HolidayAdapter.OnItemClickListener interface
        adapter.setOnItemClickListener(new HolidayAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Holiday holiday) {
                launchHolidayActivity(holiday.getId());
            }
        });

        // Set up the FAB
        FloatingActionButton fab = findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHolidayActivity(HolidayActivity.EXTRA_VALUE_NO_ID);
            }
        });

        // Request a HolidayViewModel from the Android system
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        // Observe the ViewModel's LiveData
        mViewModel.getAllHolidays().observe(this, new Observer<List<Holiday>>() {
            /**
             * Called each time the data in the LiveData object we're observing changes.
             * @param holidays
             */
            @Override
            public void onChanged(List<Holiday> holidays) {
                adapter.setHolidays(holidays);
            }
        });
    }


    // Private methods

    /**
     * Creates an Intent to start HolidayActivity, passing the given noteId as an extra.
     *
     * @param id
     */
    private void launchHolidayActivity(int id) {
        Intent intent = new Intent(this, HolidayActivity.class);
        intent.putExtra(HolidayActivity.EXTRA_ID, id);
        startActivity(intent);
    }
}
