package org.tig.android.tigadmintoolbox;

import java.util.ArrayList;

/**
 * Created by nguye on 6/11/2016.
 */
public class TIGActivityShort {
    protected String ID, name, type, location, startTime, endTime, shortIntro;

    protected ArrayList<String> memberIDCheckin;

    public TIGActivityShort(String ID, String name, String type, String location, String startTime, String endTime, String shortIntro) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.location = location;
        this.startTime = startTime;
        this.shortIntro = shortIntro;
        this.endTime = endTime;
        memberIDCheckin = new ArrayList<>();
    }

    public TIGActivityShort(){}

    public ArrayList<String> getMemberIDCheckin() {
        return memberIDCheckin;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }
}
