package com.jgendeavors.ithdayof;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HolidayActivity extends AppCompatActivity {
    // Intent Extras
    public static final String EXTRA_ID = "com.jgendeavors.ithdayof.EXTRA_ID";
    public static final int EXTRA_VALUE_NO_ID = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
    }
}
