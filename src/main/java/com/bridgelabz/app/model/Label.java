package com.bridgelabz.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Label {
    @Id
    private int labelid;
    private String labelname;
    private int userid;
    public int getLabelid() {
        return labelid;
    }
    public void setLabelid(int labelid) {
        this.labelid = labelid;
    }
    public String getLabelname() {
        return labelname;
    }
    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    @Override
    public String toString() {
        return "Label [labelid=" + labelid + ", labelname=" + labelname + ", userid=" + userid + "]";
    }
	
}
