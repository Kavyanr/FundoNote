package com.bridgelabz.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Lable {
    @Id
    private int lableid;
    private String lablename;
    private int userid;
    public int getLableid() {
        return lableid;
    }
    public void setLableid(int lableid) {
        this.lableid = lableid;
    }
    public String getLablename() {
        return lablename;
    }
    public void setLablename(String lablename) {
        this.lablename = lablename;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    @Override
    public String toString() {
        return "Lable [lableid=" + lableid + ", lablename=" + lablename + ", userid=" + userid + "]";
    }
	
}
