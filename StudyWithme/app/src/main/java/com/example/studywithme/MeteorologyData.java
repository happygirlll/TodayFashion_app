package com.example.studywithme;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeteorologyData extends Thread {
    Bundle bun = new Bundle();
    Handler handler;
    String[] todayTime = new String[8]; //오늘 시간
    String[] tomorrowTime = new String[8]; //내일 시간
    String[] aftertomorrowTime = new String[8]; //모레 시간

    //오늘 시간별
    String[] temp_today = new String[8];
    String[] weather_today = new String[8];
    String[] prob_rain_today = new String[8]; //강수확률
    String[] wind_speed_today = new String[8]; //풍속
    String[] wind_dir_today = new String[8]; //풍향

    //내일 시간별
    String[] temp_tomorrow = new String[8];
    String[] weather_tomorrow = new String[8];
    String[] prob_rain_tomorrow = new String[8]; //강수확률
    String[] wind_speed_tomorrow = new String[8]; //풍속
    String[] wind_dir_tomorrow = new String[8]; //풍향
    String[] tomorrow_aver = new String[2]; //내일 최저 최고기온

    //모레 시간별
    String[] temp_aftertomorrow = new String[8];
    String[] weather_aftertomorrow = new String[8];
    String[] prob_rain_aftertomorrow = new String[8]; //강수확률
    String[] wind_speed_aftertomorrow = new String[8]; //풍속
    String[] wind_dir_aftertomorrow = new String[8]; //풍향
    String[] afterto_aver = new String[2]; //모레 최저, 최고 기온

    String address;
    Double latitude;
    Double longitude;
    public MeteorologyData(Handler handler, String address, Double latitude, Double longitude){
        this.handler = handler;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public void run() {
        Map<String, Object> map = getGridxy(latitude, longitude);
        String url = "https://www.kma.go.kr/wid/queryDFS.jsp?gridx="+map.get("x")+"&gridy="+map.get("y");
        Document doc = null; //HTML로 부터 데이터 가져오기
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elem = doc.select("hour"); //시간
        String[] Time = elem.text().split(" ");

        for(int i=0;i<Time.length;i++) //하루시간간격
            Time[i] = Integer.toString(Integer.parseInt(Time[i])-3)+"시";

        Elements elem2 = doc.select("day"); //날짜
        String[] day = elem2.text().split(" ");

        Elements elem3 = doc.select("wfKor"); //날씨
        //구름 많음, 구름 조금은 띄어쓰기 붙이기
        String weather;
        weather = elem3.text().replace("구름 ","구름");
        String[] Weather = weather.split(" ");

        Elements elem4 = doc.select("temp"); //온도
        String[] Temp = elem4.text().split(" ");
        for(int i=0;i<Temp.length;i++) //단위붙여주기
            Temp[i] = Temp[i].replace(".0","℃");

        Elements elem5 = doc.select("pop"); //강수확률
        String[] Prob_rain = elem5.text().split(" ");
        for(int i=0;i<Prob_rain.length;i++) //단위붙여주기
            Prob_rain[i] = Prob_rain[i]+"%";

        Elements elem6 = doc.select("ws"); //풍속
        String[] Wind_speed = elem6.text().split(" ");
        for(int i=0;i<Wind_speed.length;i++) { //단위붙여주기
            Double ws = (double)Math.round(Double.parseDouble(Wind_speed[i])*100)/100; //소수점 둘째자리까지
            Wind_speed[i] = Double.toString(ws) + "m/s";
        }

        Elements elem7 = doc.select("wdKor"); //풍향
        String[] Wind_dir = elem7.text().split(" ");

        Elements elem8 = doc.select("reh"); //습도
        String[] Humidity = elem8.text().split(" ");
        for(int i=0;i<Humidity.length;i++) //단위붙여주기
            Humidity[i] = Humidity[i]+"%";

        Elements elem9 = doc.select("tmn"); //최저
        String[] Min = elem9.text().split(" ");
        for(int i=0;i<Min.length;i++) //단위붙여주기
            Min[i] = Min[i].replace(".0","℃");

        Elements elem10 = doc.select("tmx"); //최고
        String[] Max = elem10.text().split(" ");
        for(int i=0;i<Max.length;i++) //단위붙여주기
            Max[i] = Max[i].replace(".0","℃");

        List<String> list = new ArrayList<>(Arrays.asList(day));
        int index_tomorrow = list.indexOf("1"); //내일로 넘어가는 인덱스
        int index_aftertomorrow = list.indexOf("2"); //모레로 넘어가는 인덱스

        int empty = 1;
        if (index_aftertomorrow == -1 || Time.length - index_aftertomorrow < 7)
            empty = 0;

        for (int i = 0; i < 8; i++) {
            todayTime[i] = Time[i];
            tomorrowTime[i] = Time[i + index_tomorrow];

            weather_today[i] = Weather[i];
            weather_tomorrow[i] = Weather[i + index_tomorrow];

            temp_today[i] = Temp[i];
            temp_tomorrow[i] = Temp[i + index_tomorrow];

            prob_rain_today[i] = Prob_rain[i];
            prob_rain_tomorrow[i] = Prob_rain[i + index_tomorrow];

            wind_speed_today[i] = Wind_speed[i];
            wind_speed_tomorrow[i] = Wind_speed[i + index_tomorrow];

            wind_dir_today[i] = Wind_dir[i];
            wind_dir_tomorrow[i] = Wind_dir[i + index_tomorrow];
        }
        if (empty == 1) {
            for (int i = 0; i < 8; i++) {
                aftertomorrowTime[i] = Time[i + index_aftertomorrow];
                weather_aftertomorrow[i] = Weather[i + index_aftertomorrow];
                temp_aftertomorrow[i] = Temp[i + index_aftertomorrow];
                prob_rain_aftertomorrow[i] = Prob_rain[i + index_aftertomorrow];
                wind_speed_aftertomorrow[i] = Wind_speed[i + index_aftertomorrow];
                wind_dir_aftertomorrow[i] = Wind_dir[i + index_aftertomorrow];
            }
        }
        tomorrow_aver[0] = Min[index_tomorrow];
        tomorrow_aver[1] = Max[index_tomorrow];

        if (index_aftertomorrow!=-1 && !Max[index_aftertomorrow].equals("-999℃") && !Min[index_aftertomorrow].equals("-999℃")) {
            afterto_aver[0] = Min[index_aftertomorrow];
            afterto_aver[1] = Max[index_aftertomorrow];
            if(Time.length - index_aftertomorrow < 7)
                empty = 2;
        }

        //전달
        bun.putStringArray("temptoday",temp_today);
        bun.putStringArray("weathertoday",weather_today);
        bun.putStringArray("windspeedtoday",wind_speed_today);
        bun.putStringArray("winddirtoday",wind_dir_today);
        bun.putStringArray("probraintoday",prob_rain_today);
        bun.putStringArray("timetoday",todayTime);
        bun.putStringArray("timetomorrow",tomorrowTime);
        bun.putStringArray("temptomorrow",temp_tomorrow);
        bun.putStringArray("weathertomorrow",weather_tomorrow);
        bun.putStringArray("probraintomorrow",prob_rain_tomorrow);
        bun.putStringArray("tempafterto",temp_aftertomorrow);
        bun.putStringArray("weatherafterto",weather_aftertomorrow);
        bun.putStringArray("probrainafterto",prob_rain_aftertomorrow);
        bun.putStringArray("tomorrowaver",tomorrow_aver);
        bun.putStringArray("aftertoaver",afterto_aver);
        bun.putStringArray("humiditytoday",Humidity);
        bun.putInt("isempty",empty);
        Message msg = handler.obtainMessage();
        msg.setData(bun);
        handler.sendMessage(msg);
    }

    public Map<String, Object> getGridxy(double v1, double v2) {

        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)
        double DEGRAD = Math.PI / 180.0;
        // double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        Map<String, Object> map = new HashMap<String, Object>();

        double ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = v2 * DEGRAD - olon;
        if (theta > Math.PI)
            theta -= 2.0 * Math.PI;
        if (theta < -Math.PI)
            theta += 2.0 * Math.PI;
        theta *= sn;
        map.put("lat", v1);
        map.put("lng", v2);
        map.put("x", (int)Math.floor(ra * Math.sin(theta) + XO + 0.5));
        map.put("y", (int)Math.floor(ro - ra * Math.cos(theta) + YO + 0.5));

        return map;
    }
}


