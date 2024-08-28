package com.example.studywithme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    int menu;
    String address;
    Double latitude;
    Double longitude;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int menu, String address, Double latitude, Double longitude) {
        super(fm);
        this.menu = menu;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //프레그먼트 교체를 보여줌
    @NonNull
    public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragToday_meteo.newInstance(address,latitude,longitude);
                case 1:
                    return FragTomorrow_meteo.newInstance(address,latitude,longitude);
                case 2:
                    return FragAftertomorrow_meteo.newInstance(address,latitude,longitude);
                default:
                    return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "오늘";
            case 1:
                return "내일";
            case 2:
                return "모레";
            default:
                return null;
        }
    }
}
