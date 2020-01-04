package com.jgendeavors.ithdayof;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * The DAO (Data Access Object) provides abstracted access to the Room database
 * We define methods that correspond to database operations, and Room implements the methods
 * under the hood.
 *
 * Rule of thumb: create one DAO per Room entity
 */
@Dao
public interface HolidayDao {
    @Insert
    void insert(Holiday holiday);
    @Update
    void update(Holiday holiday);
    @Delete
    void delete(Holiday holiday);

    @Query("SELECT * FROM holiday_table WHERE id = :id")
    Holiday getHoliday(int id);

    @Query("SELECT * FROM holiday_table ORDER BY date DESC")
    LiveData<List<Holiday>> getHolidaysByDateDescending();
    @Query("SELECT * FROM holiday_table ORDER BY date_created DESC")
    LiveData<List<Holiday>> getHolidaysByDateCreatedDescending();
    @Query("SELECT * FROM holiday_table ORDER BY title DESC")
    LiveData<List<Holiday>> getHolidaysByTitleDescending();
}
