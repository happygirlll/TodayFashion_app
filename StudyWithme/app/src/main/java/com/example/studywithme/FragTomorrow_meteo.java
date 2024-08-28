package com.example.studywithme;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragTomorrow_meteo extends Fragment {

    private View view;
    ImageView iv_weather_morning;
    TextView tv_weather_morning;
    TextView tv_temp_morning;
    ImageView iv_weather_after;
    TextView tv_weather_after;
    TextView tv_temp_after;

    ImageView iv_weather_tomorrow1;
    ImageView iv_weather_tomorrow2;
    ImageView iv_weather_tomorrow3;
    ImageView iv_weather_tomorrow4;
    ImageView iv_weather_tomorrow5;

    TextView tv_temp_tomorrow1;
    TextView tv_temp_tomorrow2;
    TextView tv_temp_tomorrow3;
    TextView tv_temp_tomorrow4;
    TextView tv_temp_tomorrow5;

    TextView tv_probrain_tomorrow1;
    TextView tv_probrain_tomorrow2;
    TextView tv_probrain_tomorrow3;
    TextView tv_probrain_tomorrow4;
    TextView tv_probrain_tomorrow5;

    TextView tv_time_tomorrow1;
    TextView tv_time_tomorrow2;
    TextView tv_time_tomorrow3;
    TextView tv_time_tomorrow4;
    TextView tv_time_tomorrow5;

    String address;
    Double latitude;
    Double longitude;
    public static FragTomorrow_meteo newInstance(String address, Double latitude, Double longitude){
        FragTomorrow_meteo fragTomorrow = new FragTomorrow_meteo(address, latitude,longitude);
        return fragTomorrow;
    }

    FragTomorrow_meteo(String address, Double latitude, Double longitude){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_tomorrow, container,false);

        iv_weather_morning = view.findViewById(R.id.iv_weather_morning);
        tv_weather_morning = view.findViewById(R.id.tv_weather_morning);
        tv_temp_morning = view.findViewById(R.id.tv_temp_morning);
        iv_weather_after = view.findViewById(R.id.iv_weather_after);
        tv_weather_after = view.findViewById(R.id.tv_weather_after);
        tv_temp_after = view.findViewById(R.id.tv_temp_after);

        iv_weather_tomorrow1 = view.findViewById(R.id.iv_weather_tomorrow1);
        iv_weather_tomorrow2 = view.findViewById(R.id.iv_weather_tomorrow2);
        iv_weather_tomorrow3 = view.findViewById(R.id.iv_weather_tomorrow3);
        iv_weather_tomorrow4 = view.findViewById(R.id.iv_weather_tomorrow4);
        iv_weather_tomorrow5 = view.findViewById(R.id.iv_weather_tomorrow5);

        tv_temp_tomorrow1 = view.findViewById(R.id.tv_temp_tomorrow1);
        tv_temp_tomorrow2 = view.findViewById(R.id.tv_temp_tomorrow2);
        tv_temp_tomorrow3 = view.findViewById(R.id.tv_temp_tomorrow3);
        tv_temp_tomorrow4 = view.findViewById(R.id.tv_temp_tomorrow4);
        tv_temp_tomorrow5 = view.findViewById(R.id.tv_temp_tomorrow5);

        tv_probrain_tomorrow1 = view.findViewById(R.id.tv_probrain_tomorrow1);
        tv_probrain_tomorrow2 = view.findViewById(R.id.tv_probrain_tomorrow2);
        tv_probrain_tomorrow3 = view.findViewById(R.id.tv_probrain_tomorrow3);
        tv_probrain_tomorrow4 = view.findViewById(R.id.tv_probrain_tomorrow4);
        tv_probrain_tomorrow5 = view.findViewById(R.id.tv_probrain_tomorrow5);

        tv_time_tomorrow1 = view.findViewById(R.id.tv_time_tomorrow1);
        tv_time_tomorrow2 = view.findViewById(R.id.tv_time_tomorrow2);
        tv_time_tomorrow3 = view.findViewById(R.id.tv_time_tomorrow3);
        tv_time_tomorrow4 = view.findViewById(R.id.tv_time_tomorrow4);
        tv_time_tomorrow5 = view.findViewById(R.id.tv_time_tomorrow5);

        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bun = msg.getData();
                String[] temp = bun.getStringArray("temptomorrow");
                String[] time = bun.getStringArray("timetomorrow");
                String[] weather = bun.getStringArray("weathertomorrow");
                String[] probrain = bun.getStringArray("probraintomorrow");
                String[] tomorrowaver = bun.getStringArray("tomorrowaver");

                tv_temp_morning.setText(tomorrowaver[0]);
                tv_weather_morning.setText(weather[2]);
                tv_temp_after.setText(tomorrowaver[1]);
                tv_weather_after.setText(weather[6]);

                switch (weather[2]) { //오전 날씨
                    case "맑음":
                        iv_weather_morning.setImageResource(R.drawable.sun);
                        break;
                    case "흐림":
                        iv_weather_morning.setImageResource(R.drawable.cloudy);
                        break;
                    case "구름조금":
                        iv_weather_morning.setImageResource(R.drawable.cloud_little_day);
                        break;
                    case "구름많음":
                        iv_weather_morning.setImageResource(R.drawable.cloud_many_day);
                        break;
                    case "비":
                        iv_weather_morning.setImageResource(R.drawable.rainy);
                        break;
                    case "눈":
                        iv_weather_morning.setImageResource(R.drawable.snowy);
                        break;
                }
                if(weather[2].contains("비") && weather[2].contains("눈")) //비 또는 눈일때
                    iv_weather_morning.setImageResource(R.drawable.rainy_or_snowy);

                switch (weather[6]) { //오후 날씨
                    case "맑음":
                        iv_weather_after.setImageResource(R.drawable.sun);break;
                    case "흐림":
                        iv_weather_after.setImageResource(R.drawable.cloudy);break;
                    case "구름조금":
                        iv_weather_after.setImageResource(R.drawable.cloud_little_day);
                        break;
                    case "구름많음":
                        iv_weather_after.setImageResource(R.drawable.cloud_many_day);break;
                    case "비":
                        iv_weather_after.setImageResource(R.drawable.rainy);break;
                    case "눈":
                        iv_weather_after.setImageResource(R.drawable.snowy);break;
                }
                if(weather[6].contains("비") && weather[6].contains("눈")) //비 또는 눈일때
                    iv_weather_after.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_tomorrow1.setText(temp[2]);
                tv_probrain_tomorrow1.setText(probrain[2]);
                tv_time_tomorrow1.setText(time[2]);
                int n = Integer.parseInt(time[2].replace("시",""));
                switch (weather[2]) {
                    case "맑음":
                        if (n>=6 && n<18)
                            iv_weather_tomorrow1.setImageResource(R.drawable.sun);
                        else
                            iv_weather_tomorrow1.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_tomorrow1.setImageResource(R.drawable.cloudy);break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow1.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_tomorrow1.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow1.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_tomorrow1.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_tomorrow1.setImageResource(R.drawable.rainy);break;
                    case "눈":
                        iv_weather_tomorrow1.setImageResource(R.drawable.snowy);break;
                }
                if(weather[2].contains("비") && weather[2].contains("눈")) //비 또는 눈일때
                    iv_weather_tomorrow1.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_tomorrow2.setText(temp[3]);
                tv_probrain_tomorrow2.setText(probrain[3]);
                tv_time_tomorrow2.setText(time[3]);
                n = Integer.parseInt(time[3].replace("시",""));
                switch (weather[3]) {
                    case "맑음":
                        if (n>=6 && n<18)
                            iv_weather_tomorrow2.setImageResource(R.drawable.sun);
                        else
                            iv_weather_tomorrow2.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_tomorrow2.setImageResource(R.drawable.cloudy);break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow2.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_tomorrow2.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow2.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_tomorrow2.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_tomorrow2.setImageResource(R.drawable.rainy);break;
                    case "눈":
                        iv_weather_tomorrow2.setImageResource(R.drawable.snowy);break;
                }
                if(weather[3].contains("비") && weather[3].contains("눈")) //비 또는 눈일때
                    iv_weather_tomorrow2.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_tomorrow3.setText(temp[4]);
                tv_probrain_tomorrow3.setText(probrain[4]);
                tv_time_tomorrow3.setText(time[4]);
                n = Integer.parseInt(time[4].replace("시",""));

                switch (weather[4]) {
                    case "맑음":
                        if (n>=6 && n<18)
                            iv_weather_tomorrow3.setImageResource(R.drawable.sun);
                        else
                            iv_weather_tomorrow3.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_tomorrow3.setImageResource(R.drawable.cloudy);break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow3.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_tomorrow3.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow3.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_tomorrow3.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_tomorrow3.setImageResource(R.drawable.rainy);break;
                    case "눈":
                        iv_weather_tomorrow3.setImageResource(R.drawable.snowy);break;
                }
                if(weather[4].contains("비") && weather[4].contains("눈")) //비 또는 눈일때
                    iv_weather_tomorrow3.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_tomorrow4.setText(temp[5]);
                tv_probrain_tomorrow4.setText(probrain[5]);
                tv_time_tomorrow4.setText(time[5]);
                n = Integer.parseInt(time[5].replace("시",""));

                switch (weather[5]) {
                    case "맑음":
                        if (n>=6 && n<18)
                            iv_weather_tomorrow4.setImageResource(R.drawable.sun);
                        else
                            iv_weather_tomorrow4.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_tomorrow4.setImageResource(R.drawable.cloudy);break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow4.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_tomorrow4.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow4.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_tomorrow4.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_tomorrow4.setImageResource(R.drawable.rainy);break;
                    case "눈":
                        iv_weather_tomorrow4.setImageResource(R.drawable.snowy);break;
                }
                if(weather[5].contains("비") && weather[5].contains("눈")) //비 또는 눈일때
                    iv_weather_tomorrow4.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_tomorrow5.setText(temp[6]);
                tv_probrain_tomorrow5.setText(probrain[6]);
                tv_time_tomorrow5.setText(time[6]);
                n = Integer.parseInt(time[6].replace("시",""));

                switch (weather[6]) {
                    case "맑음":
                        if (n>=6 && n<18)
                            iv_weather_tomorrow5.setImageResource(R.drawable.sun);
                        else
                            iv_weather_tomorrow5.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_tomorrow5.setImageResource(R.drawable.cloudy);break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow5.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_tomorrow5.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_tomorrow5.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_tomorrow5.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_tomorrow5.setImageResource(R.drawable.rainy);break;
                    case "눈":
                        iv_weather_tomorrow5.setImageResource(R.drawable.snowy);break;
                }
                if(weather[6].contains("비") && weather[6].contains("눈")) //비 또는 눈일때
                    iv_weather_tomorrow5.setImageResource(R.drawable.rainy_or_snowy);
            }
        };
        MeteorologyData meteo = new MeteorologyData(handler, address,latitude,longitude);
        meteo.start();
        return view;
    }
}


