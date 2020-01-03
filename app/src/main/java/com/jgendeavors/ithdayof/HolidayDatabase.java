package com.jgendeavors.ithdayof;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Singleton pattern
 */
@Database(entities = {Holiday.class}, version = 1)
public abstract class HolidayDatabase extends RoomDatabase {
    private static HolidayDatabase instance;

    public abstract HolidayDao holidayDao(); // Room implements this method under the hood with the databaseBuilder() method

    public static synchronized HolidayDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HolidayDatabase.class, "holiday_database")
                    .fallbackToDestructiveMigration() // delete this database when version number is incremented
                    .build();
        }
        return instance;
    }
}
