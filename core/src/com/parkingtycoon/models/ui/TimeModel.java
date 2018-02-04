package com.parkingtycoon.models.ui;

import com.parkingtycoon.models.BaseModel;

public class TimeModel extends BaseModel {

    private long time = 0;


    public long getTime() {
        return time;
    }

    public void increaseTime() {
        this.time++;
        notifyViews();
    }

    public void setTime(long time) {
        this.time = time;
        notifyViews();
    }
}
