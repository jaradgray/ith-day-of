package com.jgendeavors.ithdayof;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class HolidayActivity extends AppCompatActivity {
    // Intent Extras
    public static final String EXTRA_ID = "com.jgendeavors.ithdayof.EXTRA_ID";
    public static final int EXTRA_VALUE_NO_ID = -1;

    // Reference to Views
    private EditText mEtTitle;
    private DatePicker mDatePicker;

    private HolidayActivityViewModel mViewModel;


    // Overridden Methods

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);

        // Get references to Views
        mEtTitle = findViewById(R.id.activity_holiday_et_title);
        mDatePicker = findViewById(R.id.activity_holiday_datepicker);

        // Set ActionBar stuff
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("");

        // set up mDatePicker
        Calendar calendar = Calendar.getInstance();
        // max date today
        mDatePicker.setMaxDate(calendar.getTimeInMillis());
        // min date a year ago yesterday
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        mDatePicker.setMinDate(calendar.getTimeInMillis());

        // Get the ViewModel that will drive this Activity's UI
        mViewModel = ViewModelProviders.of(this).get(HolidayActivityViewModel.class);

        // Set ViewModel's state based on if we're dealing with a new Holiday, or an existing Holiday
        if (getIntent().hasExtra(EXTRA_ID)) {
            int holidayId = getIntent().getIntExtra(EXTRA_ID, EXTRA_VALUE_NO_ID);
            if (holidayId == EXTRA_VALUE_NO_ID) {
                // dealing with a new Holiday
            } else {
                // dealing with an existing Holiday
                mViewModel.setHolidayById(holidayId);
            }
        }

        // Observe changes to the ViewModel's Holiday
        mViewModel.getHoliday().observe(this, new Observer<Holiday>() {
            @Override
            public void onChanged(Holiday holiday) {
                // set View data based on holiday
                Calendar calendar = Calendar.getInstance();
                final int year, month, dayOfMonth;
                if (holiday == null) {
                    // set View data to indicate Holiday not set
                    // nothing to do
                } else {
                    // set View data to match Holiday data
                    mEtTitle.setText(holiday.getTitle());
                    calendar.setTimeInMillis(holiday.getDate());
                }
                // set mDatePicker data here, for less lines of code
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                mDatePicker.updateDate(year, month, dayOfMonth);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_holiday_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save_holiday:
                if (saveHoliday()) {
                    finish();
                }
                return true;
            case R.id.menu_item_delete_holiday:
                deleteHoliday();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Private methods

    /**
     * Save this Activity's data as a Holiday via the ViewModel
     * @return true if the Holiday was successfully saved, false otherwise
     */
    private boolean saveHoliday() {
        // Get data from Views
        String title = mEtTitle.getText().toString().trim();

        // title can't be empty
        if (title.isEmpty()) {
            Toast.makeText(this, getString(R.string.activity_holiday_toast_no_title), Toast.LENGTH_SHORT).show();
            return false;
        }

        // save data as a Holiday
        final int year = mDatePicker.getYear();
        final int month = mDatePicker.getMonth();
        final int dayOfMonth = mDatePicker.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mViewModel.saveHoliday(title, calendar.getTimeInMillis());
        return true;
    }

    /**
     * Delete the Holiday we're dealing with, via the ViewModel
     */
    private void deleteHoliday() {
        mViewModel.deleteHoliday();
    }
}
