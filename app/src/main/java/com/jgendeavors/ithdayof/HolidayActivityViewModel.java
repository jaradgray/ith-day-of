package com.jgendeavors.ithdayof;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * The HolidayActivityViewModel stores and processes data that affects the UI of HolidayActivity,
 * and communicates user interaction to the model (Repository)
 */
public class HolidayActivityViewModel extends AndroidViewModel {
    private HolidayRepository mRepository;
    private MutableLiveData<Holiday> mHoliday;

    public HolidayActivityViewModel(@NonNull Application application) {
        super(application);

        mRepository = new HolidayRepository(application);
        mHoliday = new MutableLiveData<>(null);
    }

    // API methods this ViewModel exposes

    public LiveData<Holiday> getHoliday() { return mHoliday; }

    /**
     * When we're dealing with an existing Note, our NoteActivity needs to tell this ViewModel
     * which one it is so we can assign it to mNote.
     * @param id
     */
    public void setHolidayById(int id) { mHoliday.setValue(mRepository.getHoliday(id)); }
    public void deleteHoliday() { mRepository.delete(mHoliday.getValue()); }

    public void saveHoliday(/* TODO take data from views as parameters */) {
        Holiday holiday;
        if (mHoliday.getValue() == null) {
            // save new Holiday
        } else {
            // update existing Holiday
        }
        // update this ViewModel's mHoliday
        mHoliday.setValue(holiday);
    }
}
