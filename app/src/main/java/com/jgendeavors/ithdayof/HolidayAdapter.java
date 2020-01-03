package com.jgendeavors.ithdayof;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.HolidayViewHolder> {
    // ViewHolder
    public class HolidayViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDay;
        public TextView tvDate;

        public HolidayViewHolder(@NonNull View itemView) {
            super(itemView);

            // get references to Views in layout
            tvDay = itemView.findViewById(R.id.item_holiday_tv_day);
            tvDate = itemView.findViewById(R.id.item_holiday_tv_date);

            // capture clicks on this item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        mListener.onItemClicked(mHolidays.get(position));
                    }
                }
            });
        }
    }

    /**
     * The interface that reports clicks on items in this Adapter's RecyclerView
     */
    public interface OnItemClickListener {
        void onItemClicked(Holiday holiday);
    }
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }


    // Instance variables
    private List<Holiday> mHolidays = new ArrayList<Holiday>();

    // Setter

    /**
     * This is how we update our data.
     *
     * @param holidays
     */
    public void setHolidays(List<Holiday> holidays) {
        mHolidays = holidays;
        notifyDataSetChanged();
    }


    // Overridden methods

    @NonNull
    @Override
    public HolidayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holiday, parent, false);
        return new HolidayViewHolder(itemView);
    }

    /**
     * Populate item View (HolidayViewHolder) based on data from the corresponding Holiday object.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull HolidayViewHolder holder, int position) {
        Holiday holiday = mHolidays.get(position);

        // TODO set tvDay's text to number of days since holiday's date
        // TODO set tvDate's text to holiday's date
    }

    @Override
    public int getItemCount() {
        return mHolidays.size();
    }
}
