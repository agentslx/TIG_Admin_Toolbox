package org.tig.android.tigadmintoolbox;

/**
 * Created by nguye on 6/10/2016.
 */
public class TIGMemberShort {

    public static final boolean MALE = true;
    public static final boolean FEMALE = false;

    protected String ID;
    protected String fistName;
    protected String lastName;
    protected String org;
    protected String type;
    protected boolean gender = MALE;

    public TIGMemberShort() {
    }

    public TIGMemberShort(String ID, String fistName, String lastName, boolean gender, String org, String type) {
        this.ID = ID;
        this.fistName = fistName;
        this.lastName = lastName;
        this.type = type;
        this.gender = gender;
        this.org = org;
    }

    public static boolean isMALE() {
        return MALE;
    }

    public static boolean isFEMALE() {
        return FEMALE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
