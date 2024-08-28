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

public class FragToday_meteo extends Fragment {

    ImageView iv_weather_now;
    TextView tv_weather_now;
    TextView tv_temp_now;
    TextView tv_wind_dir_now;
    TextView tv_wind_speed_now;
    TextView tv_prob_rain_now;
    TextView tv_humidity_now;
    TextView tv_time_now;

    ImageView iv_weather_today1;
    ImageView iv_weather_today2;
    ImageView iv_weather_today3;
    ImageView iv_weather_today4;
    ImageView iv_weather_today5;

    TextView tv_temp_today1;
    TextView tv_temp_today2;
    TextView tv_temp_today3;
    TextView tv_temp_today4;
    TextView tv_temp_today5;

    TextView tv_probrain_today1;
    TextView tv_probrain_today2;
    TextView tv_probrain_today3;
    TextView tv_probrain_today4;
    TextView tv_probrain_today5;

    TextView tv_time_today1;
    TextView tv_time_today2;
    TextView tv_time_today3;
    TextView tv_time_today4;
    TextView tv_time_today5;
    TextView tv_smog;
    String address;
    Double latitude;
    Double longitude;

    private View view;
    public static FragToday_meteo newInstance(String address,Double latitude, Double longitude){
        FragToday_meteo fragToday = new FragToday_meteo(address,latitude,longitude);
        return fragToday;
    }

    FragToday_meteo(String address, Double latitude, Double longitude){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_today, container,false);
        iv_weather_now = view.findViewById(R.id.iv_weather_now);
        tv_weather_now = view.findViewById(R.id.tv_weather_now);
        tv_time_now = view.findViewById(R.id.tv_time_now);
        tv_temp_now = view.findViewById(R.id.tv_temp_now);
        tv_wind_dir_now = view.findViewById(R.id.tv_wind_dir_now);
        tv_wind_speed_now = view.findViewById(R.id.tv_wind_speed_now);
        tv_prob_rain_now = view.findViewById(R.id.tv_prob_rain_now);
        tv_humidity_now = view.findViewById(R.id.tv_humidity_now);

        iv_weather_today1 = view.findViewById(R.id.iv_weather_today1);
        iv_weather_today2 = view.findViewById(R.id.iv_weather_today2);
        iv_weather_today3 = view.findViewById(R.id.iv_weather_today3);
        iv_weather_today4 = view.findViewById(R.id.iv_weather_today4);
        iv_weather_today5 = view.findViewById(R.id.iv_weather_today5);

        tv_temp_today1 = view.findViewById(R.id.tv_temp_today1);
        tv_temp_today2 = view.findViewById(R.id.tv_temp_today2);
        tv_temp_today3 = view.findViewById(R.id.tv_temp_today3);
        tv_temp_today4 = view.findViewById(R.id.tv_temp_today4);
        tv_temp_today5 = view.findViewById(R.id.tv_temp_today5);

        tv_probrain_today1 = view.findViewById(R.id.tv_probrain_today1);
        tv_probrain_today2 = view.findViewById(R.id.tv_probrain_today2);
        tv_probrain_today3 = view.findViewById(R.id.tv_probrain_today3);
        tv_probrain_today4 = view.findViewById(R.id.tv_probrain_today4);
        tv_probrain_today5 = view.findViewById(R.id.tv_probrain_today5);

        tv_time_today1 = view.findViewById(R.id.tv_time_today1);
        tv_time_today2 = view.findViewById(R.id.tv_time_today2);
        tv_time_today3 = view.findViewById(R.id.tv_time_today3);
        tv_time_today4 = view.findViewById(R.id.tv_time_today4);
        tv_time_today5 = view.findViewById(R.id.tv_time_today5);

        tv_smog = view.findViewById(R.id.tv_smog);
        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bun = msg.getData();
                String[] temp = bun.getStringArray("temptoday");
                String[] timetoday = bun.getStringArray("timetoday");
                String[] weather = bun.getStringArray("weathertoday");
                String[] windspeed = bun.getStringArray("windspeedtoday");
                String[] winddir = bun.getStringArray("winddirtoday");
                String[] probrain = bun.getStringArray("probraintoday");
                String[] humidity = bun.getStringArray("humiditytoday");

                tv_time_now.setText(timetoday[0]+", 현재");
                tv_weather_now.setText(weather[0]);
                tv_temp_now.setText(temp[0]);
                tv_prob_rain_now.setText("강수확률 : " + probrain[0]);
                tv_wind_speed_now.setText("풍속 : " + windspeed[0]);
                tv_wind_dir_now.setText("풍향 : " + winddir[0]);
                tv_humidity_now.setText("습도 : " + humidity[0]);
                tv_smog.setText("미세먼지 : -");
                int n = Integer.parseInt(timetoday[0].replace("시",""));
                switch (weather[0]) {
                    case "맑음":
                        if(n >= 6 && n<18)
                            iv_weather_now.setImageResource(R.drawable.sun);
                        else
                            iv_weather_now.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_now.setImageResource(R.drawable.cloudy);
                        break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_now.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_now.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_now.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_now.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_now.setImageResource(R.drawable.rainy);
                        break;
                    case "눈":
                        iv_weather_now.setImageResource(R.drawable.snowy);
                        break;
                }
                if(weather[0].contains("비") && weather[0].contains("눈")) //비 또는 눈일때
                    iv_weather_now.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_today1.setText(temp[1]);
                tv_probrain_today1.setText(probrain[1]);
                tv_time_today1.setText(timetoday[1]);

                n = Integer.parseInt(timetoday[1].replace("시",""));
                switch (weather[1]) {
                    case "맑음":
                        if (n >= 6 && n<18)
                            iv_weather_today1.setImageResource(R.drawable.sun);
                        else
                            iv_weather_today1.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_today1.setImageResource(R.drawable.cloudy);
                        break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_today1.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_today1.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_today1.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_today1.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_today1.setImageResource(R.drawable.rainy);
                        break;
                    case "눈":
                        iv_weather_today1.setImageResource(R.drawable.snowy);
                        break;
                }
                if(weather[1].contains("비") && weather[1].contains("눈")) //비 또는 눈일때
                    iv_weather_today1.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_today2.setText(temp[2]);
                tv_probrain_today2.setText(probrain[2]);
                tv_time_today2.setText(timetoday[2]);
                n = Integer.parseInt(timetoday[2].replace("시",""));
                switch (weather[2]) {
                    case "맑음":
                        if (n >= 6 && n<18)
                            iv_weather_today2.setImageResource(R.drawable.sun);
                        else
                            iv_weather_today2.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_today2.setImageResource(R.drawable.cloudy);
                        break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_today2.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_today2.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_today2.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_today2.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_today2.setImageResource(R.drawable.rainy);
                        break;
                    case "눈":
                        iv_weather_today2.setImageResource(R.drawable.snowy);
                        break;
                }
                if(weather[2].contains("비") && weather[2].contains("눈")) //비 또는 눈일때
                    iv_weather_today2.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_today3.setText(temp[3]);
                tv_probrain_today3.setText(probrain[3]);
                tv_time_today3.setText(timetoday[3]);
                n = Integer.parseInt(timetoday[3].replace("시",""));
                switch (weather[3]) {
                    case "맑음":
                        if (n >= 6 && n<18)
                            iv_weather_today3.setImageResource(R.drawable.sun);
                        else
                            iv_weather_today3.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_today3.setImageResource(R.drawable.cloudy);
                        break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_today3.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_today3.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_today3.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_today3.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_today3.setImageResource(R.drawable.rainy);
                        break;
                    case "눈":
                        iv_weather_today3.setImageResource(R.drawable.snowy);
                        break;
                }
                if(weather[3].contains("비") && weather[3].contains("눈")) //비 또는 눈일때
                    iv_weather_today3.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_today4.setText(temp[4]);
                tv_probrain_today4.setText(probrain[4]);
                tv_time_today4.setText(timetoday[4]);
                n = Integer.parseInt(timetoday[4].replace("시",""));

                switch (weather[4]) {
                    case "맑음":
                        if (n >= 6 && n<18)
                            iv_weather_today4.setImageResource(R.drawable.sun);
                        else
                            iv_weather_today4.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_today4.setImageResource(R.drawable.cloudy);
                        break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_today4.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_today4.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_today4.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_today4.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_today4.setImageResource(R.drawable.rainy);
                        break;
                    case "눈":
                        iv_weather_today4.setImageResource(R.drawable.snowy);
                        break;
                }
                if(weather[4].contains("비") && weather[4].contains("눈")) //비 또는 눈일때
                    iv_weather_today4.setImageResource(R.drawable.rainy_or_snowy);

                tv_temp_today5.setText(temp[5]);
                tv_probrain_today5.setText(probrain[5]);
                tv_time_today5.setText(timetoday[5]);
                n = Integer.parseInt(timetoday[5].replace("시",""));

                switch (weather[5]) {
                    case "맑음":
                        if (n >= 6 && n<18)
                            iv_weather_today5.setImageResource(R.drawable.sun);
                        else
                            iv_weather_today5.setImageResource(R.drawable.night);
                        break;
                    case "흐림":
                        iv_weather_today5.setImageResource(R.drawable.cloudy);
                        break;
                    case "구름조금":
                        if (n >= 6 && n<18)
                            iv_weather_today5.setImageResource(R.drawable.cloud_little_day);
                        else
                            iv_weather_today5.setImageResource(R.drawable.cloud_little_night);
                        break;
                    case "구름많음":
                        if (n >= 6 && n<18)
                            iv_weather_today5.setImageResource(R.drawable.cloud_many_day);
                        else
                            iv_weather_today5.setImageResource(R.drawable.cloud_many_night);
                        break;
                    case "비":
                        iv_weather_today5.setImageResource(R.drawable.rainy);
                        break;
                    case "눈":
                        iv_weather_today5.setImageResource(R.drawable.snowy);
                        break;
                }
                if(weather[5].contains("비") && weather[5].contains("눈")) //비 또는 눈일때
                    iv_weather_today5.setImageResource(R.drawable.rainy_or_snowy);
            }
        };
        MeteorologyData meteo = new MeteorologyData(handler, address, latitude, longitude);
        meteo.start();
        return view;
    }
}


