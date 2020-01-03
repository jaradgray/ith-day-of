package com.jgendeavors.ithdayof;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * The ViewModel class is responsible for storing and processing data that affects the UI, and
 * for communicating user interaction to the model (Repository)
 *
 * Data stored in the ViewModel survives configuration changes, unlike data stored in an Activity or Fragment
 *
 * Remember, ViewModel outlives Activities and Fragments, so don't store references to Activity Contexts
 * or to Views that reference the Activity Context in the ViewModel. We need to pass an Application
 * (Context subclass) to our Repository, so we extend AndroidViewModel whose constructor provides one.
 */
public class MainActivityViewModel extends AndroidViewModel {
    // Instance variables
    private HolidayRepository mRepository;
    private LiveData<List<Holiday>> mAllHolidays;

    // Constructor
    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        mRepository = new HolidayRepository(application);
        mAllHolidays = mRepository.getAllHolidays();
    }

    // Data manipulation methods
    // Our Activity only has access to the ViewModel and not the Repository, so we create
    // wrapper methods for the Repository API

    public void insert(Holiday holiday) { mRepository.insert(holiday); }
    public void update(Holiday holiday) { mRepository.update(holiday); }
    public void delete(Holiday holiday) { mRepository.delete(holiday); }
    public LiveData<List<Holiday>> getAllHolidays() { return mAllHolidays; }
}
