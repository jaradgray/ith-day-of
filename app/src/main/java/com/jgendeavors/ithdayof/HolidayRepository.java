package com.jgendeavors.ithdayof;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * The Repository class is in the Android recommended architecture.
 * It provides a single point of access to potentially more than one data source.
 * However, in this app we have only one data source (the Room database)
 *
 * The Repository provides our ViewModel with a clean API for accessing our app's data
 */
public class HolidayRepository {
    // Instance variables
    private HolidayDao mHolidayDao;
    private LiveData<List<Holiday>> mAllHolidays;

    // Constructor
    public HolidayRepository(Application application) {
        HolidayDatabase database = HolidayDatabase.getInstance(application);
        mHolidayDao = database.holidayDao();
        // TODO set mAllHolidays based on order preference
        mAllHolidays = mHolidayDao.getHolidaysByDateCreatedDescending(); // Room automatically executes database operations that return LiveData on a background thread
    }


    // Data manipulation methods
    // These are the API methods the Repository exposes to the outside
    // Remember, database accesses must be performed on a background thread

    public long insert(Holiday holiday) {
        long result = -1;
        try {
            result = new InsertHolidayAsyncTask(mHolidayDao).execute(holiday).get();
        } catch (Exception e) { System.err.println("HolidayRepository.insert(Holiday) Error: " + e); }
        return result;
    }

    public void update(Holiday holiday) {
        new UpdateHolidayAsyncTask(mHolidayDao).execute(holiday);
    }

    public void delete(Holiday holiday) {
        new DeleteHolidayAsyncTask(mHolidayDao).execute(holiday);
    }

    public LiveData<List<Holiday>> getAllHolidays() {
        return mAllHolidays;
    }

    public Holiday getHoliday(int id) {
        Holiday result = null;
        try {
            result = new GetHolidayAsyncTask(mHolidayDao).execute(id).get();
        } catch (Exception e) { System.err.println("HolidayRepository.getHoliday(int) Error: " + e); }
        return result;
    }


    // AsyncTasks for performing Database operations on a background thread
    // Note: they are static so they don't have a reference to the Repository itself, which could create memory leaks

    private static class InsertHolidayAsyncTask extends AsyncTask<Holiday, Void, Long> {
        // Instance Variables
        private HolidayDao holidayDao; // since this AsyncTask is static, it doesn't have access to the repository's DAO

        // Constructor
        public InsertHolidayAsyncTask(HolidayDao holidayDao) {
            this.holidayDao = holidayDao;
        }

        @Override
        protected Long doInBackground(Holiday... holidays) {
            return holidayDao.insert(holidays[0]);
        }
    }

    private static class UpdateHolidayAsyncTask extends AsyncTask<Holiday, Void, Void> {
        // Instance Variables
        private HolidayDao holidayDao; // since this AsyncTask is static, it doesn't have access to the repository's DAO

        // Constructor
        public UpdateHolidayAsyncTask(HolidayDao holidayDao) {
            this.holidayDao = holidayDao;
        }

        @Override
        protected Void doInBackground(Holiday... holidays) {
            holidayDao.update(holidays[0]);
            return null;
        }
    }

    private static class DeleteHolidayAsyncTask extends AsyncTask<Holiday, Void, Void> {
        // Instance Variables
        private HolidayDao holidayDao; // since this AsyncTask is static, it doesn't have access to the repository's DAO

        // Constructor
        public DeleteHolidayAsyncTask(HolidayDao holidayDao) {
            this.holidayDao = holidayDao;
        }

        @Override
        protected Void doInBackground(Holiday... holidays) {
            holidayDao.delete(holidays[0]);
            return null;
        }
    }

    private static class GetHolidayAsyncTask extends AsyncTask<Integer, Void, Holiday> {
        // Instance Variables
        private HolidayDao holidayDao; // since this AsyncTask is static, it doesn't have access to the repository's DAO

        // Constructor
        public GetHolidayAsyncTask(HolidayDao holidayDao) {
            this.holidayDao = holidayDao;
        }

        // Overridden methods
        @Override
        protected Holiday doInBackground(Integer... integers) {
            return holidayDao.getHoliday(integers[0]);
        }
    }
}
