package com.jgendeavors.ithdayof;

import android.app.Application;

import java.util.Calendar;

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
    public void deleteHoliday() {
        if (mHoliday.getValue() == null){
            return;
        }

        mRepository.delete(mHoliday.getValue());
    }

    public void saveHoliday(String title, long date) {
        Holiday holiday;
        if (mHoliday.getValue() == null) {
            // save new Holiday
            final long dateCreated = Calendar.getInstance().getTimeInMillis();
            holiday = new Holiday(title, date, dateCreated);
            int id = (int)mRepository.insert(holiday);
            holiday.setId(id);
        } else {
            // update existing Holiday if its data has changed
            Holiday oldHoliday = mHoliday.getValue();
            boolean dataNotChanged = oldHoliday.getTitle().equals(title) && oldHoliday.getDate() == date;
            if (dataNotChanged) return;

            holiday = new Holiday(title, date, oldHoliday.getDateCreated());
            holiday.setId(oldHoliday.getId());
            mRepository.update(holiday);
        }
        // update this ViewModel's mHoliday
        mHoliday.setValue(holiday);
    }
}
