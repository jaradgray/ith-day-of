package com.jgendeavors.ithdayof;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The @Entity annotation tells Room to create as SQLite table for this object
 */
@Entity(tableName = "holiday_table")
public class Holiday {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "date")
    private long mDate; // millis since epoch
    @ColumnInfo(name = "date_created")
    private long mDateCreated; // millis since epoch


    // Room needs to be able to construct Note objects from database data, so we provide a
    // constructor and accessor methods to enable that.

    // Constructor
    public Holiday(String title, long date, long dateCreated) {
        mTitle = title;
        mDate = date;
        mDateCreated = dateCreated;
    }

    // Getters
    public int getId() { return mId; }
    public String getTitle() { return mTitle; }
    public long getDate() { return mDate; }
    public long getDateCreated() { return mDateCreated; }

    // Setters
    public void setId(int id) { mId = id; }
}
