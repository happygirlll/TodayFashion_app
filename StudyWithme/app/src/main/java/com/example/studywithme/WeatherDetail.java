package com.example.studywithme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class WeatherDetail extends AppCompatActivity {

    ImageView iv_title;
    ImageView iv_meteo;
    TextView tv_address;
    TextView tv_meteo_address;
    private FragmentPagerAdapter fragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_detail);
        iv_title = findViewById(R.id.iv_title);
        tv_address = findViewById(R.id.tv_address);
        iv_meteo = findViewById(R.id.iv_meteo);
        tv_meteo_address = findViewById(R.id.tv_meteo_address);
        Intent intent = getIntent();
        int menu = intent.getIntExtra("menu",0);
        String address = intent.getStringExtra("address");
        Double latitude = intent.getDoubleExtra("latitude",59);
        Double longitude = intent.getDoubleExtra("longitude",125);
        tv_address.setText(address);
        tv_meteo_address.setText(address);
        if(menu==1){
            iv_title.setImageResource(R.drawable.daumlogo);
        }
        else if(menu == 2){
            iv_title.setVisibility(View.INVISIBLE);
            iv_meteo.setVisibility(View.VISIBLE);
            tv_address.setVisibility(View.INVISIBLE);
            tv_meteo_address.setVisibility(View.VISIBLE);
        }
        ViewPager viewPager = findViewById(R.id.viewPager);
        fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), menu, address, latitude, longitude);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}