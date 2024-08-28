package com.example.studywithme;

import java.util.Date;

public class scheduleData {
    private Date date;
    private String detail;

    public scheduleData() {}

    public scheduleData(Date date, String detail) {
        this.date = date;
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}