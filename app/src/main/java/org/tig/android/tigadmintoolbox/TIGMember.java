package org.tig.android.tigadmintoolbox;

/**
 * Created by nguye on 6/8/2016.
 */
public class TIGMember extends TIGMemberShort {

    private String birthDay;

    private String tel;
    private String email;

    public TIGMember(String ID, String fistName, String lastName, String birthDay, boolean gender, String org, String type, String tel, String email) {
        this.ID = ID;
        this.fistName = fistName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.org = org;
        this.tel = tel;
        this.email = email;
        this.type = type;
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
