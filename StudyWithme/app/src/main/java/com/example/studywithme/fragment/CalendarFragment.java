package com.example.studywithme.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.studywithme.InsertActivity;
import com.example.studywithme.MainActivity_memo;
import com.example.studywithme.MemoDB;
import com.example.studywithme.MySelectorDecorator;
import com.example.studywithme.OneDayDecorator;
import com.example.studywithme.R;
import com.example.studywithme.SaturdayDecorator;
import com.example.studywithme.SundayDecorator;
import com.example.studywithme.ViewModel.CalendarFragmentViewModel;
import com.example.studywithme.scheduleData;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private List<scheduleData> scheduleList;
    private TextView scheduleTextView2;
    private TextView tv_date;
    private Button button, button2;
    private String userEmail;
    private SQLiteDatabase db;
    private MemoDB dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_calendar, container, false);

        dbHelper = new MemoDB(requireContext());
        db = dbHelper.getReadableDatabase();
        scheduleList = new ArrayList<>();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_info", requireActivity().MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");

        calendarView = view.findViewById(R.id.calendar);
        scheduleTextView2 = view.findViewById(R.id.scheduleTextView2);
        scheduleTextView2.setVisibility(View.GONE);

        tv_date = view.findViewById(R.id.tv_date);
        button = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);

        calendarView.addDecorators(new SundayDecorator(), new SaturdayDecorator(), new OneDayDecorator());
        calendarView.addDecorators(
                new MySelectorDecorator(getActivity())
        );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent project1 = new Intent(getActivity(), InsertActivity.class);
                project1.putExtra("selectedDate", tv_date.getText().toString()); // Attach the selected date
                startActivity(project1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity_memo.class);
                startActivity(intent);
            }
        });
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth() + 1;
                int dayOfMonth = date.getDay();

                String selectedDate = year + "년 " + month + "월 " + dayOfMonth + "일 선택";
                tv_date.setText(selectedDate);
                scheduleTextView2.setVisibility(View.VISIBLE);
                showSchedule2(date);
                showSchedule(date);
            }
        });

        decorateCalendar();
        decorateCalendar2();

        return view;
    }

    private void showSchedule2(CalendarDay date) {
        List<scheduleData> selectedSchedules = new ArrayList<>();
        for (scheduleData schedule : scheduleList) {
            if (isSameDate(schedule.getDate(), date.getDate())) {
                selectedSchedules.add(schedule);
            }
        }

            StringBuilder stringBuilder = new StringBuilder();
            for (scheduleData schedule : selectedSchedules) {
                stringBuilder.append(schedule.getDetail()).append("\n");
            }

    }

    private void showSchedule(CalendarDay date) {
        String dateKey = date.getYear() + "/" + (date.getMonth() + 1) + "/" + date.getDay();
        Cursor cursor = db.rawQuery("SELECT content FROM memo WHERE user_email = ? AND wdate = ?", new String[]{userEmail, dateKey});
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("content");
            if (columnIndex != -1) {
                String eventDetails = cursor.getString(columnIndex);
                scheduleTextView2.setText(eventDetails);
                scheduleTextView2.setVisibility(View.VISIBLE);
            }
        } else {
            scheduleTextView2.setVisibility(View.GONE);
        }
    }

    private boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    private void decorateCalendar() {
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                String dateKey = day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay();
                Cursor cursor = db.rawQuery("SELECT content FROM memo WHERE user_email = ? AND wdate = ?", new String[]{userEmail, dateKey});
                return cursor.moveToFirst();
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setSelectionDrawable(requireActivity().getResources().getDrawable(R.drawable.custom_date_selector));
            }
        });
    }

    private void decorateCalendar2() {
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                for (scheduleData schedule : scheduleList) {
                    if (isSameDate(schedule.getDate(), day.getDate())) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(5, Color.RED));
            }
        });
        Log.d("Decorated Dates", calendarView.getCurrentDate().toString());
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CalendarFragmentViewModel mViewModel = new ViewModelProvider(this).get(CalendarFragmentViewModel.class);
        // TODO: Use the ViewModel
    }
}