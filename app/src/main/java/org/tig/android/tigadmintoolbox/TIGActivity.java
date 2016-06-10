package org.tig.android.tigadmintoolbox;

/**
 * Created by nguye on 6/8/2016.
 */
public class TIGActivity extends TIGActivityShort {
    private String fullIntro;

    public TIGActivity(String ID, String name, String type, String location, String startTime, String endTime, String shortIntro, String fullIntro) {
        super(ID, name, type, location, startTime, shortIntro, endTime);
        this.fullIntro = fullIntro;
    }

    public String getFullIntro() {
        return fullIntro;
    }

    public void setFullIntro(String fullIntro) {
        this.fullIntro = fullIntro;
    }
}
