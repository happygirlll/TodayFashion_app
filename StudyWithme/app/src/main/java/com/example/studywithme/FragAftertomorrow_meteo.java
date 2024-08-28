package com.example.studywithme;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragAftertomorrow_meteo extends Fragment {
    private View view;

    ImageView iv_weather_morning2;
    TextView tv_weather_morning2;
    TextView tv_temp_morning2;
    ImageView iv_weather_after2;
    TextView tv_weather_after2;
    TextView tv_temp_after2;

    ImageView iv_weather_afterto1;
    ImageView iv_weather_afterto2;
    ImageView iv_weather_afterto3;
    ImageView iv_weather_afterto4;
    ImageView iv_weather_afterto5;

    TextView tv_temp_afterto1;
    TextView tv_temp_afterto2;
    TextView tv_temp_afterto3;
    TextView tv_temp_afterto4;
    TextView tv_temp_afterto5;

    TextView tv_probrain_afterto1;
    TextView tv_probrain_afterto2;
    TextView tv_probrain_afterto3;
    TextView tv_probrain_afterto4;
    TextView tv_probrain_afterto5;

    TextView tv_time_afterto1;
    TextView tv_time_afterto2;
    TextView tv_time_afterto3;
    TextView tv_time_afterto4;
    TextView tv_time_afterto5;

    String address;
    Double latitude;
    Double longitude;
    LinearLayout Linear_inform;
    TextView tv_nodata;
    TextView tv_noinform;
    LinearLayout timeline_aftertomorrow;

    public static FragAftertomorrow_meteo newInstance(String address, Double latitude, Double longitude) {
        FragAftertomorrow_meteo fragAfterto = new FragAftertomorrow_meteo(address, latitude, longitude);
        return fragAfterto;
    }

    FragAftertomorrow_meteo(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_aftertomorrow, container, false);
        iv_weather_morning2 = view.findViewById(R.id.iv_weather_morning2);
        tv_weather_morning2 = view.findViewById(R.id.tv_weather_morning2);
        tv_temp_morning2 = view.findViewById(R.id.tv_temp_morning2);
        iv_weather_after2 = view.findViewById(R.id.iv_weather_after2);
        tv_weather_after2 = view.findViewById(R.id.tv_weather_after2);
        tv_temp_after2 = view.findViewById(R.id.tv_temp_after2);

        iv_weather_afterto1 = view.findViewById(R.id.iv_weather_afterto1);
        iv_weather_afterto2 = view.findViewById(R.id.iv_weather_afterto2);
        iv_weather_afterto3 = view.findViewById(R.id.iv_weather_afterto3);
        iv_weather_afterto4 = view.findViewById(R.id.iv_weather_afterto4);
        iv_weather_afterto5 = view.findViewById(R.id.iv_weather_afterto5);

        tv_temp_afterto1 = view.findViewById(R.id.tv_temp_afterto1);
        tv_temp_afterto2 = view.findViewById(R.id.tv_temp_afterto2);
        tv_temp_afterto3 = view.findViewById(R.id.tv_temp_afterto3);
        tv_temp_afterto4 = view.findViewById(R.id.tv_temp_afterto4);
        tv_temp_afterto5 = view.findViewById(R.id.tv_temp_afterto5);

        tv_probrain_afterto1 = view.findViewById(R.id.tv_probrain_afterto1);
        tv_probrain_afterto2 = view.findViewById(R.id.tv_probrain_afterto2);
        tv_probrain_afterto3 = view.findViewById(R.id.tv_probrain_afterto3);
        tv_probrain_afterto4 = view.findViewById(R.id.tv_probrain_afterto4);
        tv_probrain_afterto5 = view.findViewById(R.id.tv_probrain_afterto5);

        tv_time_afterto1 = view.findViewById(R.id.tv_time_afterto1);
        tv_time_afterto2 = view.findViewById(R.id.tv_time_afterto2);
        tv_time_afterto3 = view.findViewById(R.id.tv_time_afterto3);
        tv_time_afterto4 = view.findViewById(R.id.tv_time_afterto4);
        tv_time_afterto5 = view.findViewById(R.id.tv_time_afterto5);
        Linear_inform = view.findViewById(R.id.Linear_inform);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        tv_noinform = view.findViewById(R.id.tv_noinform);
        timeline_aftertomorrow = view.findViewById(R.id.timeline_aftertomorrow);

        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bun = msg.getData();
                String[] time = bun.getStringArray("timetomorrow");
                String[] temp = bun.getStringArray("tempafterto");
                String[] weather = bun.getStringArray("weatherafterto");
                String[] probrain = bun.getStringArray("probrainafterto");
                String[] aftertoaver = bun.getStringArray("aftertoaver");
                int isempty = bun.getInt("isempty");
                tv_temp_morning2.setText(aftertoaver[0]);
                tv_weather_morning2.setText(weather[2]);
                tv_temp_after2.setText(aftertoaver[1]);
                tv_weather_after2.setText(weather[6]);

                if (isempty == 0) { //정보가 아예 없을때
                    Linear_inform.setVisibility(View.INVISIBLE);
                    tv_nodata.setVisibility(view.VISIBLE);
                } else if (isempty == 2) { //최저, 최고온도 정보는 있고 시간대별은 없을때
                    switch (weather[2]) { //오전 날씨
                        case "맑음":
                            iv_weather_morning2.setImageResource(R.drawable.sun);
                            break;
                        case "흐림":
                            iv_weather_morning2.setImageResource(R.drawable.cloudy);
                            break;
                        case "구름조금":
                            iv_weather_morning2.setImageResource(R.drawable.cloud_little_day);break;
                        case "구름많음":
                            iv_weather_morning2.setImageResource(R.drawable.cloud_many_day);break;
                        case "비":
                            iv_weather_morning2.setImageResource(R.drawable.rainy);
                            break;
                        case "눈":
                            iv_weather_morning2.setImageResource(R.drawable.snowy);
                            break;
                    }
                    if(weather[2].contains("비") && weather[2].contains("눈")) //비 또는 눈일때
                        iv_weather_morning2.setImageResource(R.drawable.rainy_or_snowy);

                    switch (weather[6]) { //오후 날씨
                        case "맑음":
                            iv_weather_after2.setImageResource(R.drawable.sun);break;
                        case "흐림":
                            iv_weather_after2.setImageResource(R.drawable.cloudy);break;
                        case "구름조금":
                            iv_weather_after2.setImageResource(R.drawable.cloud_little_day);break;
                        case "구름많음":
                            iv_weather_after2.setImageResource(R.drawable.cloud_many_day);break;
                        case "비":
                            iv_weather_after2.setImageResource(R.drawable.rainy);break;
                        case "눈":
                            iv_weather_after2.setImageResource(R.drawable.snowy);break;
                    }
                    if(weather[6].contains("비") && weather[6].contains("눈")) //비 또는 눈일때
                        iv_weather_after2.setImageResource(R.drawable.rainy_or_snowy);

                    timeline_aftertomorrow.setVisibility(View.INVISIBLE);
                    tv_noinform.setVisibility(view.VISIBLE);
                } else { //모두 있을 때
                    switch (weather[2]) { //오전 날씨
                        case "맑음":
                            iv_weather_morning2.setImageResource(R.drawable.sun);
                            break;
                        case "흐림":
                            iv_weather_morning2.setImageResource(R.drawable.cloudy);
                            break;
                        case "구름조금":
                            iv_weather_morning2.setImageResource(R.drawable.cloud_little_day);break;
                        case "구름많음":
                            iv_weather_morning2.setImageResource(R.drawable.cloud_many_day);break;
                        case "비":
                            iv_weather_morning2.setImageResource(R.drawable.rainy);
                            break;
                        case "눈":
                            iv_weather_morning2.setImageResource(R.drawable.snowy);
                            break;
                    }
                    if(weather[2].contains("비") && weather[2].contains("눈")) //비 또는 눈일때
                        iv_weather_morning2.setImageResource(R.drawable.rainy_or_snowy);

                    switch (weather[6]) { //오후 날씨
                        case "맑음":
                            iv_weather_after2.setImageResource(R.drawable.sun);break;
                        case "흐림":
                            iv_weather_after2.setImageResource(R.drawable.cloudy);break;
                        case "구름조금":
                            iv_weather_after2.setImageResource(R.drawable.cloud_little_day);break;
                        case "구름많음":
                            iv_weather_after2.setImageResource(R.drawable.cloud_many_day);break;
                        case "비":
                            iv_weather_after2.setImageResource(R.drawable.rainy);break;
                        case "눈":
                            iv_weather_after2.setImageResource(R.drawable.snowy);break;
                    }
                    if(weather[6].contains("비") && weather[6].contains("눈")) //비 또는 눈일때
                        iv_weather_after2.setImageResource(R.drawable.rainy_or_snowy);

                    tv_temp_afterto1.setText(temp[2]);
                    tv_probrain_afterto1.setText(probrain[2]);
                    tv_time_afterto1.setText(time[2]);
                    int n = Integer.parseInt(time[2].replace("시",""));
                    switch (weather[2]) {
                        case "맑음":
                            if (n>=6 && n<18)
                                iv_weather_afterto1.setImageResource(R.drawable.sun);
                            else
                                iv_weather_afterto1.setImageResource(R.drawable.night);
                            break;
                        case "흐림":
                            iv_weather_afterto1.setImageResource(R.drawable.cloudy);break;
                        case "구름조금":
                            if (n >= 6 && n<18)
                                iv_weather_afterto1.setImageResource(R.drawable.cloud_little_day);
                            else
                                iv_weather_afterto1.setImageResource(R.drawable.cloud_little_night);
                            break;
                        case "구름많음":
                            if (n >= 6 && n<18)
                                iv_weather_afterto1.setImageResource(R.drawable.cloud_many_day);
                            else
                                iv_weather_afterto1.setImageResource(R.drawable.cloud_many_night);
                            break;
                        case "비":
                            iv_weather_afterto1.setImageResource(R.drawable.rainy);break;
                        case "눈":
                            iv_weather_afterto1.setImageResource(R.drawable.snowy);break;
                    }
                    if(weather[2].contains("비") && weather[2].contains("눈")) //비 또는 눈일때
                        iv_weather_afterto1.setImageResource(R.drawable.rainy_or_snowy);

                    tv_temp_afterto2.setText(temp[3]);
                    tv_probrain_afterto2.setText(probrain[3]);
                    tv_time_afterto2.setText(time[3]);
                    n = Integer.parseInt(time[3].replace("시",""));
                    switch (weather[3]) {
                        case "맑음":
                            if (n>=6 && n<18)
                                iv_weather_afterto2.setImageResource(R.drawable.sun);
                            else
                                iv_weather_afterto2.setImageResource(R.drawable.night);
                            break;
                        case "흐림":
                            iv_weather_afterto2.setImageResource(R.drawable.cloudy);break;
                        case "구름조금":
                            if (n >= 6 && n<18)
                                iv_weather_afterto2.setImageResource(R.drawable.cloud_little_day);
                            else
                                iv_weather_afterto2.setImageResource(R.drawable.cloud_little_night);
                            break;
                        case "구름많음":
                            if (n >= 6 && n<18)
                                iv_weather_afterto2.setImageResource(R.drawable.cloud_many_day);
                            else
                                iv_weather_afterto2.setImageResource(R.drawable.cloud_many_night);
                            break;
                        case "비":
                            iv_weather_afterto2.setImageResource(R.drawable.rainy);break;
                        case "눈":
                            iv_weather_afterto2.setImageResource(R.drawable.snowy);break;
                    }
                    if(weather[3].contains("비") && weather[3].contains("눈")) //비 또는 눈일때
                        iv_weather_afterto2.setImageResource(R.drawable.rainy_or_snowy);

                    tv_temp_afterto3.setText(temp[4]);
                    tv_probrain_afterto3.setText(probrain[4]);
                    tv_time_afterto3.setText(time[4]);
                    n = Integer.parseInt(time[4].replace("시",""));
                    switch (weather[4]) {
                        case "맑음":
                            if (n>=6 && n<18)
                                iv_weather_afterto3.setImageResource(R.drawable.sun);
                            else
                                iv_weather_afterto3.setImageResource(R.drawable.night);
                            break;
                        case "흐림":
                            iv_weather_afterto3.setImageResource(R.drawable.cloudy);break;
                        case "구름조금":
                            if (n >= 6 && n<18)
                                iv_weather_afterto3.setImageResource(R.drawable.cloud_little_day);
                            else
                                iv_weather_afterto3.setImageResource(R.drawable.cloud_little_night);
                            break;
                        case "구름많음":
                            if (n >= 6 && n<18)
                                iv_weather_afterto3.setImageResource(R.drawable.cloud_many_day);
                            else
                                iv_weather_afterto3.setImageResource(R.drawable.cloud_many_night);
                            break;
                        case "비":
                            iv_weather_afterto3.setImageResource(R.drawable.rainy);break;
                        case "눈":
                            iv_weather_afterto3.setImageResource(R.drawable.snowy);break;
                    }
                    if(weather[4].contains("비") && weather[4].contains("눈")) //비 또는 눈일때
                        iv_weather_afterto3.setImageResource(R.drawable.rainy_or_snowy);

                    tv_temp_afterto4.setText(temp[5]);
                    tv_probrain_afterto4.setText(probrain[5]);
                    tv_time_afterto4.setText(time[5]);
                    n = Integer.parseInt(time[5].replace("시",""));
                    switch (weather[5]) {
                        case "맑음":
                            if (n>=6 && n<18)
                                iv_weather_afterto4.setImageResource(R.drawable.sun);
                            else
                                iv_weather_afterto4.setImageResource(R.drawable.night);
                            break;
                        case "흐림":
                            iv_weather_afterto4.setImageResource(R.drawable.cloudy);break;
                        case "구름조금":
                            if (n >= 6 && n<18)
                                iv_weather_afterto4.setImageResource(R.drawable.cloud_little_day);
                            else
                                iv_weather_afterto4.setImageResource(R.drawable.cloud_little_night);
                            break;
                        case "구름많음":
                            if (n >= 6 && n<18)
                                iv_weather_afterto4.setImageResource(R.drawable.cloud_many_day);
                            else
                                iv_weather_afterto4.setImageResource(R.drawable.cloud_many_night);
                            break;
                        case "비":
                            iv_weather_afterto4.setImageResource(R.drawable.rainy);break;
                        case "눈":
                            iv_weather_afterto4.setImageResource(R.drawable.snowy);break;
                    }
                    if(weather[5].contains("비") && weather[5].contains("눈")) //비 또는 눈일때
                        iv_weather_afterto4.setImageResource(R.drawable.rainy_or_snowy);

                    tv_temp_afterto5.setText(temp[6]);
                    tv_probrain_afterto5.setText(probrain[6]);
                    tv_time_afterto5.setText(time[6]);
                    n = Integer.parseInt(time[6].replace("시",""));
                    switch (weather[6]) {
                        case "맑음":
                            if (n>=6 && n<18)
                                iv_weather_afterto5.setImageResource(R.drawable.sun);
                            else
                                iv_weather_afterto5.setImageResource(R.drawable.night);
                            break;
                        case "흐림":
                            iv_weather_afterto5.setImageResource(R.drawable.cloudy);break;
                        case "구름조금":
                            if (n >= 6 && n<18)
                                iv_weather_afterto5.setImageResource(R.drawable.cloud_little_day);
                            else
                                iv_weather_afterto5.setImageResource(R.drawable.cloud_little_night);
                            break;
                        case "구름많음":
                            if (n >= 6 && n<18)
                                iv_weather_afterto5.setImageResource(R.drawable.cloud_many_day);
                            else
                                iv_weather_afterto5.setImageResource(R.drawable.cloud_many_night);
                            break;
                        case "비":
                            iv_weather_afterto5.setImageResource(R.drawable.rainy);break;
                        case "눈":
                            iv_weather_afterto5.setImageResource(R.drawable.snowy);break;
                    }
                    if(weather[6].contains("비") && weather[6].contains("눈")) //비 또는 눈일때
                        iv_weather_afterto5.setImageResource(R.drawable.rainy_or_snowy);
                }
            }
        };
        MeteorologyData meteo = new MeteorologyData(handler, address, latitude, longitude);
        meteo.start();
        return view;
    }
}


